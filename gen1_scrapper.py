import requests
import json
# get name, types, id ;
#array
jsonarray = []
for i in range(1,152):
    print(i)
    r = requests.get("http://pokeapi.co/api/v2/pokemon/" + str(i));
    data = r.json()

    typearray = [data["types"][0]["type"]["name"]]

    if len(data["types"]) > 1:
        typearray.append(data["types"][1]["type"]["name"])

    jsonarray.append({ "name":data["name"],"types":typearray,"id":data["id"],"spriteURI":data["sprites"]["front_default"]})
    


with open('app/src/main/assets/pokemon_cards.json', 'w') as outfile:
    json.dump(jsonarray, outfile)
