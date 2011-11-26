#include "hello.h"
#include "ui_hello.h"

hello::hello(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::hello)
{
    ui->setupUi(this);
    connect(ui->pushButton, SIGNAL(clicked()), this, SLOT(buttonClicked()));
    connect(ui->pushButton_2, SIGNAL(clicked()), this, SLOT(buttonClicked()));

}

hello::~hello()
{
    delete ui;
}

void hello::buttonClicked(){
    ui->lineEdit->setText("Hello!");
}
