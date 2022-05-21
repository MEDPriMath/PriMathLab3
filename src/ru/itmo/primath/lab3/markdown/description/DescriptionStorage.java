package ru.itmo.primath.lab3.markdown.description;

public class DescriptionStorage {
    public static String htmlPre = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\" />\n" +
            "        <title>HTML Preview(markdown to html) - Editor.md examples</title>\n" +
            "        <link rel=\"stylesheet\" href=\"editor/examples/css/style.css\" />\n" +
            "        <link rel=\"stylesheet\" href=\"editor/css/editormd.preview.css\" />\n" +
            "        <style>            \n" +
            "            .editormd-html-preview {\n" +
            "                width: 90%;\n" +
            "                margin: 0 auto;\n" +
            "            }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div id=\"layout\">\n" +
            "            <div id=\"test-editormd-view2\">\n" +
            "                <textarea id=\"append-test\" style=\"display:none;\">";
    public static String htmlPost = "                </textarea>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <!-- <script src=\"js/zepto.min.js\"></script>\n" +
            "\t\t<script>\t\t\n" +
            "\t\t\tvar jQuery = Zepto;  // 为了避免修改flowChart.js和sequence-diagram.js的源码，所以使用Zepto.js时想支持flowChart/sequenceDiagram就得加上这一句\n" +
            "\t\t</script> -->\n" +
            "        <script src=\"editor/examples/js/jquery.min.js\"></script>\n" +
            "        <script src=\"editor/lib/marked.min.js\"></script>\n" +
            "        <script src=\"editor/lib/prettify.min.js\"></script>\n" +
            "        \n" +
            "        <script src=\"editor/lib/raphael.min.js\"></script>\n" +
            "        <script src=\"editor/lib/underscore.min.js\"></script>\n" +
            "        <script src=\"editor/lib/sequence-diagram.min.js\"></script>\n" +
            "        <script src=\"editor/lib/flowchart.min.js\"></script>\n" +
            "        <script src=\"editor/lib/jquery.flowchart.min.js\"></script>\n" +
            "\n" +
            "        <script src=\"editor/editormd.js\"></script>\n" +
            "        <script type=\"text/javascript\">\n" +
            "            $(function() {\n" +
            "                var testEditormdView, testEditormdView2;\n" +
            "                \n" +
            "                $.get(\"test.md\", function(markdown) {\n" +
            "                    \n" +
            "\t\t\t\t    testEditormdView = editormd.markdownToHTML(\"test-editormd-view\", {\n" +
            "                        markdown        : markdown ,//+ \"\\r\\n\" + $(\"#append-test\").text(),\n" +
            "                        //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启\n" +
            "                        htmlDecode      : \"style,script,iframe\",  // you can filter tags decode\n" +
            "                        //toc             : false,\n" +
            "                        tocm            : true,    // Using [TOCM]\n" +
            "                        //tocContainer    : \"#custom-toc-container\", // 自定义 ToC 容器层\n" +
            "                        //gfm             : false,\n" +
            "                        //tocDropdown     : true,\n" +
            "                        // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签\n" +
            "                        emoji           : true,\n" +
            "                        taskList        : true,\n" +
            "                        tex             : true,  // 默认不解析\n" +
            "                        flowChart       : true,  // 默认不解析\n" +
            "                        sequenceDiagram : true,  // 默认不解析\n" +
            "                    });\n" +
            "                    \n" +
            "                    //console.log(\"返回一个 jQuery 实例 =>\", testEditormdView);\n" +
            "                    \n" +
            "                    // 获取Markdown源码\n" +
            "                    //console.log(testEditormdView.getMarkdown());\n" +
            "                    \n" +
            "                    //alert(testEditormdView.getMarkdown());\n" +
            "                });\n" +
            "                    \n" +
            "                testEditormdView2 = editormd.markdownToHTML(\"test-editormd-view2\", {\n" +
            "                    htmlDecode      : \"style,script,iframe\",  // you can filter tags decode\n" +
            "                    emoji           : true,\n" +
            "                    taskList        : true,\n" +
            "                    tex             : true,  // 默认不解析\n" +
            "                    flowChart       : true,  // 默认不解析\n" +
            "                    sequenceDiagram : true,  // 默认不解析\n" +
            "                });\n" +
            "            });\n" +
            "        </script>\n" +
            "    </body>\n" +
            "</html>";
    public static String luDescription =
            "LU разложение - разложение матрицы A на две матрицы, такие что:\n" +
            "L - нижнетреугольная матрица\n" +
            "U - верхнетреугольная матрица\n" +
            "L∙U = A";
    public static String jacobiDescription = "Метод Якоби:\n" +
            "1. Когда использовать?\n" +
            " Когда матрица слишком большая и метод Гауса не эффективен/затратен\n" +
            "\n" +
            "2. Какие условия?\n" +
            " Диагональные коэффиценты не равны нулю\n" +
            "\n" +
            "3. Ход решения:\n" +
            "\n" +
            "Имеем СЛАУ вида Ax = b\n" +
            "\n" +
            "Метод Якоби:\n" +
            "\n" +
            "1. Когда использовать?\n" +
            "Когда матрица слишком большая и метод Гауса не эффективен/затратен\n" +
            "2. Какие условия?\n" +
            "Диагональные коeффиценты не равны нулю\n" +
            "3. Ход решения:\n" +
            "Имеем СЛАУ вида Ax= b\n" +
            "Матрица имеет вид A = L \\* U \\* D\n" +
            "Тогда СЛАУ можно записать в виде Lx + Ux + Dx = b\n" +
            "Преобразуем выражение и получим:\n" +
            "$$x = -D^{-1}  (L + U) + b  D ^ {-1}$$\n" +
            "$$D^{-1} (L + U) = B$$\n" +
            "$$b  D^{-1} = g$$\n" +
            "Записываем все в виде итерационного метода:\n" +
            "$$x^{k+1} = Bx^{k} + g$$   (k - итерация)\n" +
            "Переводим в поэлементную формулу и получаем\n" +
            "$$ x\\_i ^ {k+1}  = (b\\_i - a\\_{ij} x\\_j ^ k - a\\_{ij+1} x\\_{j+1} ^ k - .. - a\\_{in} x\\_n ^ k)$$\n" +
            "$$i \\neq j $$\n" +
            "Для сходимости достаточно, чтобы ||B|| был меньше 1\n" +
            "Скорость сходимости геометрической прогрессии\n" +
            "    \n" +
            "    Условие выхода: $$||x^{k+1} - x^k|| < \\epsilon$$";
    public static String invertMatrixDescription = "";
}
