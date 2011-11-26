''' Greatest Common Divisor finding '''
def euclid(numA, numB):
    while numB != 0:
        numRem = numA % numB
        numA = numB
        numB = numRem
    return numA

input = raw_input('input numbers: ')
while input != '0 0':
    a = input.split(' ')[0]
    b = input.split(' ')[1]
    print 'GCD(' + str(a) + ',' + str(b) + ') = ' + str(euclid(int(a), int(b)))
    input = raw_input('input numbers: ')

