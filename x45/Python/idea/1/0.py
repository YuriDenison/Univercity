# -*- coding: utf-8 -*-

credit = {'amount': int(raw_input('Amount of credit: ')),
          'term': int(raw_input('Credit term (months): ')),
          'interest rate': (float(raw_input('Interest rate: '))),
          'start month': int(raw_input('Month of loan: ')),
          'start year': int(raw_input('Year of loan: '))}

print 'Кредит на сумму', int(credit['amount']), 'тугриков взят',
print str(credit['start month']) + '.' + str(credit['start year']), 'на', int(credit['term']),

# в зависимости от величины term будут разные окончания у слова 'месяц'
if credit['term'] % 10 == 1 & int(credit['term']) / 10 != 1:
    print 'месяц'
else:
    if credit['term'] % 10 < 5 & int(credit['term']) / 10 != 1:
        print 'месяца'
    else:
        print 'месяцев'
print 'Ставка', credit['interest rate'], '% годовых'

monthRest = credit['term']
curMonth = credit['start month']
curYear = credit['start year']
curRest = credit['amount']
monthPercent = pow(credit['interest rate'] / 100 + 1, (1.0/12.0)) - 1

while monthRest != 0:
    monthRest -= 1
    if curMonth > 12:    # переход через Новый Год
        curMonth = 1
        curYear += 1
    curRest -= credit['amount'] / credit['term']
    curPay = credit['amount'] / credit['term'] + curRest * monthPercent
    print 'Выплата', str(curMonth) + '.' + str(curYear), 'составляет', curPay, 'тугриков.'
    curMonth += 1

