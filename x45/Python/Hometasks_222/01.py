# -*- coding: utf-8 -*-

amountOfCredit = int(raw_input('Amount of credit: '))    # общая сумма кредита
term = int(raw_input('Credit term (months): '))          # срок кредита в месяцах
startDate = raw_input('Date of loan: ')          # дата взятия кредита
startMonth = startDate[1:'.']
startYear = startDate['.':]
print startMonth, ' ', startYear
