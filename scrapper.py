import requests
import json
from functools import reduce
from pprint import pprint
import threading
from retrying import retry


def find(lst, f):
    return list(filter(lambda x: f(x), lst))[0]

def get_pokemon_moves(data):
    ret = []

    for move in data['moves']:
        r = requests.get(move['move']['url'])
        m = r.json()

        try:
            level = find(move['version_group_details'], lambda x: x['move_learn_method']['name'] == 'level-up' and x['version_group']['name'] == 'firered-leafgreen')['level_learned_at']
            ret.append({
                'method': level,
                'name': find(m['names'], lambda x: x['language']['name'] == 'en')['name'],
                'type': m['type']['name'],
                'category': m['damage_class']['name'],
                'pp': m['pp'],
                'power': m['power'],
                'accuracy': m['accuracy']
            })
        except:
            continue

    return ret

def get_pokemon_evolutions(chain):
    if not chain['evolves_to']:
        return [{'id': int(chain['species']['url'].split('/')[-2]), 'name': chain['species']['name']}]

    # huge simplification here by picking the first possible evolution in the chain
    return [{'id': int(chain['species']['url'].split('/')[-2]), 'name': chain['species']['name']}] + get_pokemon_evolutions(chain['evolves_to'][0])

@retry(wait_random_min=1800000, wait_random_max=3600000)
def get_pokemon(i):
    pokemon_id = str(i)

    r = requests.get("http://pokeapi.co/api/v2/pokemon/" + pokemon_id);
    data = r.json()

    types = [data['types'][0]['type']['name'], data['types'][1]['type']['name']] if len(data['types']) > 1 else [data['types'][0]['type']['name']]

    moves = get_pokemon_moves(data)

    r = requests.get("http://pokeapi.co/api/v2/pokemon-species/" + pokemon_id)
    species = r.json()

    try:
        r = requests.get(species['evolution_chain']['url'])
        chain = r.json()
        evolutions = get_pokemon_evolutions(chain['chain'])
    except:
        evolutions = []

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
    n = 151
    pokemons = []

    for i in range(1, n+1):
        pokemons.append(get_pokemon(i))
        print(pokemons[-1])
        print()
        print()
        with open('app/src/main/assets/pokedex.json', 'w') as f:
            json.dump(pokemons, f)

if __name__ == "__main__":
    main()

