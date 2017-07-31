import random, commands

i = 0

while i < 100:
	v = random.randrange(4, 20)
	e = str(random.randrange(v, v*2))
	c = str(random.randrange(1, 50))

	v = str(v)

	#commands.getoutput("/info/adk16/labb3/flowgen " + v + " " + e + " " + c + " > " + str(i) + ".txt")
	commands.getoutput("/info/adk16/labb3/maxflow < ../flows/" + str(i) + ".txt > " + str(i) + ".txt")
	
	i += 1
