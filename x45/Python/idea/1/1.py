# -*- coding: utf-8 -*-

dict = {'ATT': 'I','ATC': 'I','ATA': 'I',
        'CTT': 'L','CTC': 'L','CTA': 'L','CTG': 'L','TTA': 'L','TTG': 'L',
        'GTT': 'V','GTC': 'V','GTA': 'V','GTG': 'V',
        'TTT': 'F','TTC': 'F',
        'ATG': 'M',
        'TGT': 'C','TGC': 'C',
        'GCT': 'A','GCC': 'A','GCA': 'A','GCG': 'A',
        'GGT': 'G','GGC': 'G','GGA': 'G','GGG': 'G',
        'CCT': 'P','CCC': 'P','CCA': 'P','CCG': 'P',
        'ACT': 'T','ACC': 'T','ACA': 'T','ACG': 'T',
        'TCT': 'S','TCC': 'S','TCA': 'S','TCG': 'S','AGT': 'S','AGC': 'S',
        'TAT': 'Y','TAC': 'Y',
        'TGG': 'W',
        'CAA': 'Q','CAG': 'Q',
        'AAT': 'N','AAC': 'N',
        'CAT': 'H','CAC': 'H',
        'GAA': 'E','GAG': 'E',
        'GAT': 'D','GAC': 'D',
        'AAA': 'K','AAG': 'K',
        'CGT': 'R','CGC': 'R','CGA': 'R','CGG': 'R','AGA': 'R','AGG': 'R',
        'TAA': 'STOP','TAG': 'STOP','TGA': 'STOP'}

def kodon(str):
    return dict[str]

str = raw_input('enter kodon: ')
print kodon(str)
