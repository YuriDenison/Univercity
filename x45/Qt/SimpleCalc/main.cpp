#include <QApplication>

#include "calc.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    Calc calculator;
    calculator.show();
    return app.exec();
}
