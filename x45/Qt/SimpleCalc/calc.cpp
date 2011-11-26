#include "calc.h"
#include "ui_calc.h"

Calc::Calc(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Calc)
{
    QStringList list;
    list.push_front("-");
    list.push_front("+");
    ui->setupUi(this);
    connect(ui->number1, SIGNAL(valueChanged(int)), this, SLOT(getResult()));
    connect(ui->number2, SIGNAL(valueChanged(int)), this, SLOT(getResult()));
    connect(ui->sign, SIGNAL(currentIndexChanged(QString)), this, SLOT(getResult()));
    ui->sign->addItems(list);
}

Calc::~Calc()
{
    delete ui;
}

void Calc::getResult(){
    int a = ui->number1->value();
    int b = ui->number2->value();
    int res = 0;
    if(ui->sign->currentText() == "+")
        res = a+b;
    if(ui->sign->currentText() == "-")
        res = a-b;
    ui->result->setText(QString::number(res));
}

