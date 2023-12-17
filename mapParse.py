f = open("map.txt", "r").readlines()
for idx, i in enumerate(f):
    f[idx] = f[idx].strip("").split(",")
    for idx2, j in enumerate(f[idx]):
        f[idx][idx2] = f[idx][idx2].zfill(2)

out = open("map1.txt", "w")
out.write(str(len(f[0])) + "\n")
out.write(str(len(f)) + "\n")
for i in range(len(f)):
    for j in range(len(f[i])):
        out.write(str(f[i][j]))
        if(j != len(f[i]) - 1):
            out.write(" ")
