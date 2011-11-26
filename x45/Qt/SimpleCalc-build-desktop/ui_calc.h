/********************************************************************************
** Form generated from reading UI file 'calc.ui'
**
** Created: Fri Oct 1 19:29:04 2010
**      by: Qt User Interface Compiler version 4.6.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_CALC_H
#define UI_CALC_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QComboBox>
#include <QtGui/QHBoxLayout>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QLineEdit>
#include <QtGui/QMainWindow>
#include <QtGui/QMenuBar>
#include <QtGui/QSpinBox>
#include <QtGui/QStatusBar>
#include <QtGui/QToolBar>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Calc
{
public:
    QWidget *centralWidget;
    QWidget *widget;
    QHBoxLayout *horizontalLayout;
    QSpinBox *number1;
    QComboBox *sign;
    QSpinBox *number2;
    QLabel *label;
    QLineEdit *result;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *Calc)
    {
        if (Calc->objectName().isEmpty())
            Calc->setObjectName(QString::fromUtf8("Calc"));
        Calc->resize(400, 300);
        centralWidget = new QWidget(Calc);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        widget = new QWidget(centralWidget);
        widget->setObjectName(QString::fromUtf8("widget"));
        widget->setGeometry(QRect(20, 40, 328, 27));
        horizontalLayout = new QHBoxLayout(widget);
        horizontalLayout->setSpacing(6);
        horizontalLayout->setContentsMargins(11, 11, 11, 11);
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        number1 = new QSpinBox(widget);
        number1->setObjectName(QString::fromUtf8("number1"));

        horizontalLayout->addWidget(number1);

        sign = new QComboBox(widget);
        sign->setObjectName(QString::fromUtf8("sign"));
        sign->setEditable(false);

        horizontalLayout->addWidget(sign);

        number2 = new QSpinBox(widget);
        number2->setObjectName(QString::fromUtf8("number2"));

        horizontalLayout->addWidget(number2);

        label = new QLabel(widget);
        label->setObjectName(QString::fromUtf8("label"));
        QSizePolicy sizePolicy(QSizePolicy::Minimum, QSizePolicy::Minimum);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(label->sizePolicy().hasHeightForWidth());
        label->setSizePolicy(sizePolicy);

        horizontalLayout->addWidget(label);

        result = new QLineEdit(widget);
        result->setObjectName(QString::fromUtf8("result"));

        horizontalLayout->addWidget(result);

        Calc->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(Calc);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 400, 23));
        Calc->setMenuBar(menuBar);
        mainToolBar = new QToolBar(Calc);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        Calc->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(Calc);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        Calc->setStatusBar(statusBar);

        retranslateUi(Calc);

        QMetaObject::connectSlotsByName(Calc);
    } // setupUi

    void retranslateUi(QMainWindow *Calc)
    {
        Calc->setWindowTitle(QApplication::translate("Calc", "Calc", 0, QApplication::UnicodeUTF8));
        label->setText(QApplication::translate("Calc", "=", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class Calc: public Ui_Calc {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_CALC_H
