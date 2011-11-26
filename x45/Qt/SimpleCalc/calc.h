#ifndef CALC_H
#define CALC_H

#include <QMainWindow>
#include <QApplication>

namespace Ui {
    class Calc;
}

class Calc : public QMainWindow
{
    Q_OBJECT

public:
    explicit Calc(QWidget *parent = 0);
    ~Calc();

private:
    Ui::Calc *ui;

private slots:
    void getResult();
};

#endif // CALC_H
