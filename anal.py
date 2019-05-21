import json

with open("maxcut.out.sequence", "r") as f:
	lines = [line.replace("\n", "").strip() for line in f.readlines()]
	for line in lines:
		line = json.loads(line)
		print(f"{line['gen']},{line['score']},{line['avg']},{line['sim']}")

