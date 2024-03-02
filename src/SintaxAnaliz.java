import java.util.*;

public class SintaxAnaliz extends LeksAnaliz {
    private ArrayList<Token> Output;
    private ListIterator<Token> iter;
    private Token token;

    public SintaxAnaliz() {
        super("Dora.txt");
        Output = analiz();
        iter = Output.listIterator();
        token = new Token("", "");
    }

    public Token Scan() {
        if (!iter.hasNext()) {
            return null;
        }
        token = iter.next();
        return token;
    }

    public Token ScanV() {
        if (iter.hasNext()) {
            Token nextToken = iter.next();
            iter.previous();
            return nextToken;
        } else {
            return null;
        }
    }

    public void checkArithmeticExpression() {
        try {
            PRG();

            System.out.println();
            System.out.println("Синтаксис: Валидные арифметические выражения");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void E() {
        T();
        while (token != null && (token.getValue().equals(GetIndex(GetList('B'), "+") + "")
                && token.getTypeValue().equals("B")
                || token.getValue().equals(GetIndex(GetList('B'), "-") + "") && token.getTypeValue().equals("B"))) {
            token = Scan();
            token = ScanV();

            if (token != null && !(token.getTypeValue().equals("C") || token.getTypeValue().equals("I") ||
                    (token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "(") + "")))) {
                System.out.println();
                System.out.println("Error: Отсутствует выражение");
                System.exit(0);
            }
            T();
        }
    }

    public void T() {
        F();
        while (token != null && (token.getValue().equals(GetIndex(GetList('B'), "*") + "")
                && token.getTypeValue().equals("B")
                || token.getValue().equals(GetIndex(GetList('B'), "/") + "") && token.getTypeValue().equals("B"))) {
            token = Scan();
            token = ScanV();

            if (token != null && !(token.getTypeValue().equals("C") || token.getTypeValue().equals("I") ||
                    (token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "(") + "")))) {
                System.out.println();
                System.out.println("Error: Отсутствует выражение");
                System.exit(0);
            }

            F();
        }
    }

    public void F() {
        token = ScanV();
        if (token != null && token.getTypeValue().equals("C")) {
            token = Scan();
            token = ScanV();
        } else if (token != null && token.getTypeValue().equals("I")) {
            token = Scan();
            token = ScanV();

            if (token != null && token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), "[") + "")) {
                token = Scan();
                token = ScanV();

                if (token != null && token.getTypeValue().equals("B")
                        && token.getValue().equals(GetIndex(GetList('B'), "]") + "")) {
                    System.out.println();
                    System.out.println("Error: Отсутствует идентификатор");
                    System.exit(0);
                }

                E();

                if (token != null && token.getTypeValue().equals("B")
                        && token.getValue().equals(GetIndex(GetList('B'), "]") + "")) {
                    token = Scan();
                    token = ScanV();
                } else {
                    System.out.println();
                    System.out.println("Error: Отсутствует ]");
                    System.exit(0);
                }
            }
        }
        if (token != null && token.getTypeValue().equals("B")
                && token.getValue().equals(GetIndex(GetList('B'), "(") + "")) {
            token = Scan();
            token = ScanV();
            E();
            if (token != null && token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), ")") + "")) {
                token = Scan();
                token = ScanV();
            } else {
                System.out.println();
                System.out.println("Error: Отсутствует )");
                System.exit(0);
            }
        }
        token = ScanV();
    }

    public void Logical_V() {
        Logical_T();

        while (token != null && (token.getValue().equals(GetIndex(GetList('K'), "or") + "") &&
                token.getTypeValue().equals("K"))) {
            token = Scan();
            token = ScanV();

            if (!(token != null && (token.getTypeValue().equals("C") ||
                    token.getTypeValue().equals("I") ||
                    (token.getTypeValue().equals("B")
                            && token.getValue().equals(GetIndex(GetList('B'), "(") + ""))
                    || (token.getValue().equals(GetIndex(GetList('K'), "true") + "")
                            && token.getTypeValue().equals("K"))
                    || (token.getValue().equals(GetIndex(GetList('K'), "false") + "")
                            && token.getTypeValue().equals("K"))))) {
                System.out.println();
                System.out.println("Error: Незавершенное выражение после оператора [or]");
                System.exit(0);
            }
            Logical_T();
        }
    }

    public void Logical_T() {
        Logical_Z();

        while (token != null && (token.getValue().equals(GetIndex(GetList('K'), "and") + "") &&
                token.getTypeValue().equals("K"))) {
            token = Scan();
            token = ScanV();

            if (!(token != null && (token.getTypeValue().equals("C") ||
                    token.getTypeValue().equals("I") ||
                    (token.getTypeValue().equals("B")
                            && token.getValue().equals(GetIndex(GetList('B'), "(") + ""))
                    || (token.getValue().equals(GetIndex(GetList('K'), "true") + "")
                            && token.getTypeValue().equals("K"))
                    || (token.getValue().equals(GetIndex(GetList('K'), "false") + "")
                            && token.getTypeValue().equals("K"))))) {
                System.out.println();
                System.out.println("Error: Незавершенное выражение после оператора [and]");
                System.exit(0);
            }
            Logical_Z();
        }
    }

    public void Logical_Z() {
        Logical_F();
        while (token != null
                && (token.getValue().equals(GetIndex(GetList('B'), "<") + "")
                        && token.getTypeValue().equals("B")
                        || token.getValue().equals(GetIndex(GetList('B'), "=") + "")
                                && token.getTypeValue().equals("B")
                        || token.getValue().equals(GetIndex(GetList('B'), ">") + "")
                                && token.getTypeValue().equals("B")
                        || token.getValue().equals(GetIndex(GetList('D'), "<=") + "")
                                && token.getTypeValue().equals("D")
                        || token.getValue().equals(GetIndex(GetList('D'), "==") + "")
                                && token.getTypeValue().equals("D")
                        || token.getValue().equals(GetIndex(GetList('D'), ">=") + "")
                                && token.getTypeValue().equals("D")
                        || token.getValue().equals(GetIndex(GetList('D'), "<>") + "")
                                && token.getTypeValue().equals("D"))) {
            token = Scan();
            token = ScanV();

            if (!(token != null && (token.getTypeValue().equals("C") ||
                    token.getTypeValue().equals("I") ||
                    (token.getTypeValue().equals("B")
                            && token.getValue().equals(GetIndex(GetList('B'), "(") + ""))
                    || (token.getValue().equals(GetIndex(GetList('K'), "true") + "")
                            && token.getTypeValue().equals("K"))
                    || (token.getValue().equals(GetIndex(GetList('K'), "false") + "")
                            && token.getTypeValue().equals("K"))))) {
                System.out.println();
                System.out.println("Error: Незавершенное выражение после оператора сравнения");
                System.exit(0);
            }
            Logical_F();
        }
    }

    public void Logical_F() {
        token = ScanV();
        if (token != null && token.getTypeValue().equals("C")) {
            token = Scan();
            token = ScanV();
        } else if (token != null && token.getTypeValue().equals("I")) {
            token = Scan();
            token = ScanV();

            if (token != null && token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), "[") + "")) {
                token = Scan();
                token = ScanV();
                E();
                if (token != null && token.getTypeValue().equals("B")
                        && token.getValue().equals(GetIndex(GetList('B'), "]") + "")) {
                    token = Scan();
                    token = ScanV();
                } else {
                    System.out.println();
                    System.out.println("Error: Отсутствует ]");
                    System.exit(0);
                }
            }
        } else if ((token.getValue().equals(GetIndex(GetList('K'), "true") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
        } else if ((token.getValue().equals(GetIndex(GetList('K'), "false") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
        }
        if (token != null && token.getTypeValue().equals("B")
                && token.getValue().equals(GetIndex(GetList('B'), "(") + "")) {
            token = Scan();
            token = ScanV();
            E();
            if (token != null && token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), ")") + "")) {
                token = Scan();
                token = ScanV();
            } else {
                System.out.println();
                System.out.println("Error: Отсутствует )");
                System.exit(0);
            }
        }
        if (token != null && (token.getValue().equals(GetIndex(GetList('B'), "*") + "")
                && token.getTypeValue().equals("B")
                || token.getValue().equals(GetIndex(GetList('B'), "/") + "")
                        && token.getTypeValue().equals("B")
                || token.getValue().equals(GetIndex(GetList('B'), "+") + "")
                        && token.getTypeValue().equals("B")
                || token.getValue().equals(GetIndex(GetList('B'), "-") + "")
                        && token.getTypeValue().equals("B"))) {
            E();
        }
        token = ScanV();
    }

    public void DP() {
        token = ScanV();
        if (!token.getTypeValue().equals("I")) {
            if (!((token.getValue().equals(GetIndex(GetList('B'), ";") + "") && token.getTypeValue().equals("B")))) {
                System.out.println();
                System.out.println("Ожидался идентификатор");
                System.exit(0);
            } else
                return;
        }

        while (token != null) {
            if (token.getTypeValue().equals("I")) {
                token = Scan();
                token = ScanV();

                if (token != null && token.getTypeValue().equals("B")
                        && token.getValue().equals(GetIndex(GetList('B'), ":") + "")) {
                    token = Scan();
                    TIP();

                } else if (token != null && token.getTypeValue().equals("B")
                        && token.getValue().equals(GetIndex(GetList('B'), ",") + "")) {

                    token = Scan();
                    token = ScanV();

                    if (token != null && (token.getTypeValue().equals("B")
                            && token.getValue().equals(GetIndex(GetList('B'), ",") + "")
                            || token.getTypeValue().equals("B")
                                    && token.getValue().equals(GetIndex(GetList('B'), ":") + ""))) {

                        System.out.println();
                        System.out.println("Error: Ожидался идентификатор");
                        System.exit(0);
                    }
                } else {
                    System.out.println();
                    System.out.println("Error: Ожидалось : или ,");
                    System.exit(0);
                }
            } else {
                break;
            }
        }
    }

    public void TIP() {
        token = ScanV();
        if (token != null && (token.getValue().equals(GetIndex(GetList('K'), "array") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
            token = ScanV();
            if (token == null || !(token.getValue().equals(GetIndex(GetList('B'), "[") + "")
                    && token.getTypeValue().equals("B"))) {
                System.out.println();
                System.out.println("Error: Ожидался [");
                System.exit(0);
            } else {
                token = Scan();
                token = ScanV();
            }

            if (token != null && token.getTypeValue().equals("C")
                    || token.getTypeValue().equals("L")) {
                token = Scan();
                token = ScanV();
            } else {
                System.out.println();
                System.out.println("Error: Ожидался число или литерал");
                System.exit(0);
            }

            if (token == null || !(token.getValue().equals(GetIndex(GetList('D'), "..") + "")
                    && token.getTypeValue().equals("D"))) {
                System.out.println();
                System.out.println("Error: Ожидался ..");
                System.exit(0);
            } else {
                token = Scan();
                token = ScanV();
            }

            if (token != null && token.getTypeValue().equals("C")
                    || token.getTypeValue().equals("L")) {
                token = Scan();
                token = ScanV();
            } else {
                System.out.println();
                System.out.println("Error: Ожидался число или литерал");
                System.exit(0);
            }

            if (token == null || !(token.getValue().equals(GetIndex(GetList('B'), "]") + "")
                    && token.getTypeValue().equals("B"))) {
                System.out.println();
                System.out.println("Error: Ожидался ]");
                System.exit(0);
            } else {
                token = Scan();
                token = ScanV();
            }

            if (token == null || !(token.getValue().equals(GetIndex(GetList('K'), "of") + "")
                    && token.getTypeValue().equals("K"))) {
                System.out.println();
                System.out.println("Error: Ожидался of");
                System.exit(0);
            } else {
                token = Scan();
                Simple();
            }
        } else
            Simple();
    }

    public void Simple() {
        token = ScanV();

        if (token != null && (token.getValue().equals(GetIndex(GetList('K'), "integer") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
            return;
        } else if (token != null && (token.getValue().equals(GetIndex(GetList('K'), "boolean") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
            return;
        } else if (token != null && (token.getValue().equals(GetIndex(GetList('K'), "char") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
            return;
        } else if (token != null && (token.getValue().equals(GetIndex(GetList('K'), "string") + "")
                && token.getTypeValue().equals("K"))) {
            token = Scan();
            return;
        } else {
            System.out.println();
            System.out.println("Error: Ожидался тип данных");
            System.exit(0);
        }
    }

    public void BLD() {
        token = ScanV();
        if (token != null && ((token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "var") + ""))
                || (token.getTypeValue().equals("K")
                        && token.getValue().equals(GetIndex(GetList('K'), "const") + "")))) {
            token = Scan();
        } else {
            System.out.println();
            System.out.println("Error: Отсутствует раздел описания переменной");
            System.exit(0);
        }
        while (token != null && !(token.getValue().equals(GetIndex(GetList('K'), "begin") + "")
                && token.getTypeValue().equals("K"))) {
            DP();
            token = ScanV();

            if (token != null && (token.getValue().equals(GetIndex(GetList('B'), ";") + "")
                    && token.getTypeValue().equals("B"))) {
                token = Scan();
            } else {
                System.out.println();
                System.out.println("Error: Ожидался ;");
                System.exit(0);
            }
            token = ScanV();
        }
    }

    public void BLO() {
        token = Scan();

        if (token != null && token.getTypeValue().equals("M")) {
            token = Scan();
        }

        if (token == null || !(token.getValue().equals(GetIndex(GetList('K'), "begin") + "")
                && token.getTypeValue().equals("K"))) {
            System.out.println();
            System.out.println("Error: Ожидался begin");
            System.exit(0);
        }

        token = ScanV();

        while (token != null && !(token.getValue().equals(GetIndex(GetList('K'), "end") + "")
                && token.getTypeValue().equals("K"))) {

            if (token != null && token.getTypeValue().equals("M")) {
                token = Scan();
            }

            token = ScanV();

            OPR();

            token = ScanV();

            if (token != null && (token.getValue().equals(GetIndex(GetList('B'), ";") + "")
                    && token.getTypeValue().equals("B"))) {
                token = Scan();
            } else if (token != null && (token.getValue().equals(GetIndex(GetList('K'), "else") + "")
                    && token.getTypeValue().equals("K"))) {
                token = Scan();
            } else {
                if (token == null || !token.getTypeValue().equals("K")
                        && !token.getValue().equals(GetIndex(GetList('K'), "end") + "")) {
                    System.out.println();
                    System.out.println("Error: Ожидался end");
                    System.exit(0);
                }
                token = ScanV();
            }
            token = ScanV();
        }
    }

    public void OPR() {
        if (token != null && token.getTypeValue().equals("K")) {
            if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "if") + "")) {
                IFELSE();
                return;
            } else if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "while") + "")) {
                WHILE();
                return;
            } else if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "write") + "")) {
                WRITE();
                return;
            } else if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "for") + "")) {
                FOR();
                return;
            } else if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "read") + "")) {
                READ();
                return;
            } else if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "let") + "")) {
                PRO();
                return;
            } else if (token != null && token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('K'), "end") + "")) {
                return;
            }
        } else if (token.getTypeValue().equals("M")) {
            token = Scan();
        } else {
            if (token == null || !token.getTypeValue().equals("I")) {
                if (!(token.getValue().equals(GetIndex(GetList('B'), ";") + "") && token.getTypeValue().equals("B"))) {
                    System.out.println();
                    System.out.println("Error: Неопознанный оператор OPR");
                    System.exit(0);
                }
            } else {
                PRO();
            }
        }
    }

    public void IFELSE() {
        token = Scan();
        token = ScanV();

        if (token != null && (token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "then") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось логическое выражение");
            System.exit(0);
        }

        Logical_V();

        token = Scan();

        if (token == null || !(token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "then") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось then");
            System.exit(0);
        }

        BLO();

        if (!(token.getTypeValue().equals("K") && token.getValue().equals(GetIndex(GetList('K'), "end") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось end");
            System.exit(0);
        }

        token = Scan();
        token = ScanV();

        if (token != null && token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "else") + "")) {
            token = Scan();

            BLO();

            if (token == null || !(token.getTypeValue().equals("K")
                    && token.getValue().equals(GetIndex(GetList('k'), "end") + ""))) {
                System.out.println();
                System.out.println("Error: Ожидалось end");
                System.exit(0);
            }
            token = Scan();
        }
    }

    public void WRITE() {
        token = Scan();
        token = ScanV();

        if (!(token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "(") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось (");
            System.exit(0);
        }

        token = Scan();
        token = ScanV();

        while (!(token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), ")") + ""))) {
            if (!token.getTypeValue().equals("L")) {
                E();
            } else {
                token = Scan();
            }

            token = ScanV();

            if (token.getTypeValue().equals("L") || token.getTypeValue().equals("C")
                    || token.getTypeValue().equals("I")) {
                System.out.println();
                System.out.println("Error: Ожидалось ,");
                System.exit(0);
            }

            if ((token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), ",") + ""))) {
                token = Scan();
            } else if (!(token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), ")") + ""))) {
                System.out.println();
                System.out.println("Error: Ожидалось )");
                System.exit(0);
            }
        }
        token = Scan();
        token = Scan();

        if (!(token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), ";") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось ;");
            System.exit(0);
        }

    }

    public void WHILE() {
        token = Scan();
        token = ScanV();

        if (token != null && token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "do") + "")) {
            System.out.println();
            System.out.println("Error: Ожидалось логическое выражение");
            System.exit(0);
        }

        Logical_V();

        token = Scan();

        if (token == null || !(token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "do") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось do");
            System.exit(0);
        }

        BLO();

        if (token == null || !(token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "end") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось end");
            System.exit(0);
        }

        token = Scan();
    }

    public void READ() {
        token = Scan();
        token = ScanV();

        if (token != null && token.getTypeValue().equals("B")
                && token.getValue().equals(GetIndex(GetList('B'), "(") + "")) {
            token = Scan();
        } else {
            System.out.println();
            System.out.println("Error: Ожидалось (");
            System.exit(0);
        }

        token = ScanV();

        while (token != null && !(token.getTypeValue().equals("B")
                && token.getValue().equals(GetIndex(GetList('B'), ")") + ""))) {
            if (token.getTypeValue().equals("I")) {
                token = Scan();
                token = ScanV();

                if (token.getTypeValue().equals("B")
                        && token.getValue().equals(GetIndex(GetList('B'), "[") + "")) {
                    token = Scan();
                    token = ScanV();

                    if (token.getTypeValue().equals("I") || token.getTypeValue().equals("C")) {
                        token = Scan();
                        token = ScanV();

                        if (token.getTypeValue().equals("B")
                                && token.getValue().equals(GetIndex(GetList('B'), "]") + "")) {
                            token = Scan();
                            token = ScanV();

                            if (token.getTypeValue().equals("I")) {
                                System.out.println();
                                System.out.println("Error: Ожидалось ,");
                                System.exit(0);
                            }
                        } else {
                            System.out.println();
                            System.out.println("Error: Ожидалось ]");
                            System.exit(0);
                        }
                    } else {
                        System.out.println();
                        System.out.println("Error: Ожидалось значение в скобках");
                        System.exit(0);
                    }
                }

                if (token.getTypeValue().equals("I")) {
                    System.out.println();
                    System.out.println("Error: Ожидалось ,");
                    System.exit(0);
                }

            } else {
                System.out.println();
                System.out.println("Error: Ожидался идентификатор");
                System.exit(0);
            }

            if (token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), "]") + "")) {
                System.out.println();
                System.out.println("Error: Ожидалось [");
                System.exit(0);
            }

            token = ScanV();

            if ((token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), ",") + ""))) {
                token = Scan();
            } else if (!(token.getTypeValue().equals("B")
                    && token.getValue().equals(GetIndex(GetList('B'), ")") + ""))) {
                System.out.println();
                System.out.println("Error: Ожидалось )");
                System.exit(0);
            }

            token = ScanV();
        }
        token = Scan();
        token = Scan();

        if (!(token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), ";") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось ;");
            System.exit(0);
        }
    }

    public void FOR() {
        token = Scan();
        token = ScanV();

        if (token != null && token.getTypeValue().equals("I")) {
            token = Scan();
            token = ScanV();

            if (token != null && token.getTypeValue().equals("D")
                    && token.getValue().equals(GetIndex(GetList('D'), ":=") + "")) {
                token = Scan();
                token = ScanV();

                if (token != null && token.getTypeValue().equals("C")) {
                    token = Scan();
                    token = ScanV();
                } else {
                    System.out.println();
                    System.out.println("Error: Ожидалось начальное значение");
                    System.exit(0);
                }

            } else {
                System.out.println();
                System.out.println("Error: Ожидалось :=");
                System.exit(0);
            }

        } else {
            System.out.println();
            System.out.println("Error: Ожидался идентификатор");
            System.exit(0);
        }

        token = ScanV();

        if (token != null && !(token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "to") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось to");
            System.exit(0);
        } else {
            token = Scan();
        }

        token = ScanV();

        if (token != null && !(token.getTypeValue().equals("C") || token.getTypeValue().equals("I"))) {
            System.out.println();
            System.out.println("Error: Ожидалось крайнее значение");
            System.exit(0);
        } else {
            token = Scan();
        }

        token = ScanV();

        BLO();

        if (token == null || !(token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "end") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось end");
            System.exit(0);
        }

        token = Scan();
    }

    public void PRO() {
        token = ScanV();

        if (token != null && token.getTypeValue().equals("K")
                && token.getValue().equals(GetIndex(GetList('K'), "let") + "")) {
            token = Scan();
        } else {
            System.out.println();
            System.out.println("Error: Ожидалось let");
            System.exit(0);
        }

        token = ScanV();

        if (token != null && token.getTypeValue().equals("I") || token.getTypeValue().equals("C")) {
            token = Scan();
            token = ScanV();

            if ((token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "[") + ""))) {
                token = Scan();
                token = ScanV();

                if ((token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "]") + ""))) {
                    System.out.println();
                    System.out.println("Error: Ожидался идентификатор");
                    System.exit(0);
                }

                E();

                token = Scan();
                if (!(token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "]") + ""))) {
                    System.out.println();
                    System.out.println("Error: Ожидалось ]");
                    System.exit(0);
                }
                token = ScanV();
            }

            if ((token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), "]") + ""))) {
                System.out.println();
                System.out.println("Error: Ожидалось [");
                System.exit(0);
            }

        } else {
            System.out.println();
            System.out.println("Error: Ожидался идентификатор");
            System.exit(0);
        }

        token = Scan();

        if (!(token.getTypeValue().equals("D") && token.getValue().equals(GetIndex(GetList('D'), ":=") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось :=");
            System.exit(0);
        }
        token = ScanV();
        E();

        token = Scan();

        if (!(token.getTypeValue().equals("B") && token.getValue().equals(GetIndex(GetList('B'), ";") + ""))) {
            System.out.println();
            System.out.println("Error: Ожидалось ;");
            System.exit(0);
        }
    }

    public void PRG() {
        token = Scan();

        if (token == null || !(token.getValue().equals(GetIndex(GetList('K'), "program") + "")
                && token.getTypeValue().equals("K"))) {
            System.out.println();
            System.out.println("Error: Ожидалось program");
            System.exit(0);
        }

        token = Scan();

        if (token == null || !token.getTypeValue().equals("I")) {
            System.out.println();
            System.out.println("Error: Ожидалось название программы");
            System.exit(0);
        }

        token = Scan();

        if (token == null
                || !(token.getValue().equals(GetIndex(GetList('B'), ";") + "") && token.getTypeValue().equals("B"))) {
            System.out.println();
            System.out.println("Error: Ожидалось ;");
            System.exit(0);
        }

        BLD();
        BLO();

        token = Scan();

        if (token == null
                || !(token.getValue().equals(GetIndex(GetList('K'), "end") + "") && token.getTypeValue().equals("K"))) {
            System.out.println();
            System.out.println("Error: Ожидалось end");
            System.exit(0);
        }

        token = Scan();

        if (token == null || !(token.getValue().equals(GetIndex(GetList('B'), ".") + "") &&
                token.getTypeValue().equals("B"))) {
            System.out.println();
            System.out.println("Error: Ожидалось .");
            System.exit(0);
        }
    }
}
