import requests
import json
from functools import reduce
from pprint import pprint
import threading


def find(lst, f):
    return list(filter(lambda x: f(x), lst))[0]

def get_pokemon_moves(data):
    moves = []
    urls = [move['move']['url'] for move in data['moves']]

    i = 0
    for url in urls:
        r = requests.get(url)
        move = r.json()

        moves.append({
            'method': 'Level 80',
            'name': find(move['names'], lambda x: x['language']['name'] == 'en')['name'],
            'type': move['type']['name'],
            'category': move['damage_class']['name'],
            'pp': move['pp'],
            'power': move['power'],
            'accuracy': move['accuracy']
        })

        if i == 5:
            break
        i+=1
        print(moves[-1])

    return moves

def get_pokemon_evolutions(chain):
    if not chain['evolves_to']:
        return [{'id': int(chain['species']['url'].split('/')[-2]), 'name': chain['species']['name']}]

    # huge simplification here by picking the first possible evolution in the chain
    return [{'id': int(chain['species']['url'].split('/')[-2]), 'name': chain['species']['name']}] + get_pokemon_evolutions(chain['evolves_to'][0])

def get_pokemon(i):
    pokemon_id = str(i)

    r = requests.get("http://pokeapi.co/api/v2/pokemon/" + pokemon_id);
    data = r.json()

    types = [data['types'][0]['type']['name'], data['types'][1]['type']['name']] if len(data['types']) > 1 else [data['types'][0]['type']['name']]

    moves = get_pokemon_moves(data)

    r = requests.get("http://pokeapi.co/api/v2/pokemon-species/" + pokemon_id)
    species = r.json()

    r = requests.get(species['evolution_chain']['url'])
    chain = r.json()
    evolutions = get_pokemon_evolutions(chain['chain'])

    r = requests.get("http://pokeapi.co/api/v2/pokemon/" + pokemon_id + "/encounters")
    encounters = reduce(lambda x, y: x + [y['location_area']['name']], r.json(), [])
    locations = list(filter(lambda x: x.startswith('kanto'), encounters))

    return {
        'id': data['id'],
        'name': data['name'].capitalize(),
        'types': types,
        'sprite': data['sprites']['front_default'],
        'locations': locations,
        'description': find(species['flavor_text_entries'], lambda x: x['language']['name'] == 'en')['flavor_text'].replace('\n', ' '),
        'hp': find(data['stats'], lambda x: x['stat']['name'] == 'hp')['base_stat'],
        'attack': find(data['stats'], lambda x: x['stat']['name'] == 'attack')['base_stat'],
        'defense': find(data['stats'], lambda x: x['stat']['name'] == 'defense')['base_stat'],
        'special_attack': find(data['stats'], lambda x: x['stat']['name'] == 'special-attack')['base_stat'],
        'special_defense': find(data['stats'], lambda x: x['stat']['name'] == 'special-defense')['base_stat'],
        'speed': find(data['stats'], lambda x: x['stat']['name'] == 'speed')['base_stat'],
        'moves': moves,
        'evolutions': evolutions
    }

def main():
    n = 20
    pokemons = []

    for i in range(1, n+1):
        print(i)
        pokemons.append(get_pokemon(i))
        print(pokemons[-1])

    with open('app/src/main/assets/pokedex.json', 'w') as f:
        json.dump(pokemons, f)

if __name__ == "__main__":
    main()

