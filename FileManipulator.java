import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManipulator {
    public static final String[] INPUTS = { "reverse", "copy", "duplicate-contents", "replace-string" };

    /**
     * 実行クラス
     * 
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("実行引数が入力されていません。");
            System.exit(1);
        }

        if (!isValidInputsPrompt(args[0])) {
            System.err.println("プロンプト（第一引数）の値が不正です。");
            System.exit(1);
        }

        if (!isValidInputsCount(args)) {
            System.err.println("コマンド引数の数が不正です。");
            System.exit(1);
        }

        // プロンプト（第一引数）を取得する
        int inputsType = switch (args[0]) {
            case "reverse" -> 0;
            case "copy" -> 1;
            case "duplicate-contents" -> 2;
            case "replace-string" -> 3;
            default -> -1;
        };

        if (inputsType == 0) {
            if (!isValidFile(args, inputsType)) {
                System.err.println("格納ファイルが不正です。");
                System.exit(1); // エラー終了
            }
            execReverse(args[1], args[2]);
        }

        if (inputsType == 1) {
        }
        if (inputsType == 2) {
        }
        if (inputsType == 3) {
        }
    }

    /**
     * 指定された入力ファイルを読み込んで内容を逆順にし、出力ファイルに書き込む
     * 
     * @param inputPath
     * @param outputPath
     */
    private static void execReverse(String inputPath, String outputPath) {
        ArrayList<String> newArrayList = new ArrayList<>();

        try {
            // inputを読み込み、reverseするところまで
            BufferedReader reader = new BufferedReader(new FileReader(inputPath));

            ArrayList<String> tmpArrayList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                tmpArrayList.add(line);
            }

            for (var i = tmpArrayList.size() - 1; i >= 0; i--) {
                newArrayList.add(tmpArrayList.get(i));
            }

        } catch (IOException e) {
            System.err.println("読み込み失敗: " + e.getMessage());
        }

        // inputをoutputに書き込みまで
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", false))) {
            for (var txt : newArrayList) {
                writer.write(txt);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("書き込み失敗: " + e.getMessage());
        }
    }

    /**
     * プロンプト（第一引数）の入力が適切か判定する
     * 
     * @param prompt
     * @return boolean
     */
    private static boolean isValidInputsPrompt(String prompt) {
        if (Arrays.asList(INPUTS).contains(prompt)) {
            return true;
        }
        return false;
    }

    /**
     * プロンプト（第一引数）の入力に応じて、適切な引数の入力数か判定する
     * 
     * @param inputs
     * @return boolean
     */
    private static boolean isValidInputsCount(String[] inputs) {
        int inputsCount = switch (inputs[0]) {
            case "reverse", "copy", "duplicate-contents" -> 3;
            case "replace-string" -> 4;
            default -> -1;
        };

        if (inputsCount < 0) {
            return false;
        }

        return inputs.length == inputsCount ? true : false;
    }

    /**
     * プロンプト（第一引数）の入力に応じて、ファイルの形式が適用か判定する
     * 
     * @param inputs
     * @param inputsType
     * @return boolean
     */
    private static boolean isValidFile(String[] inputs, int inputsType) {
        Path inputPath;
        Path outputPath;
        if (inputsType == 0) {
            inputPath = Paths.get(inputs[1]);
            outputPath = Paths.get(inputs[2]);

            // パスが正しくパースできるか、存在するかをチェック
            if (!Files.exists(inputPath) || !Files.exists(outputPath)) {
                return false;
            }

            // ファイルかどうか（ディレクトリではない）もチェック
            if (!Files.isRegularFile(inputPath) || !Files.isRegularFile(outputPath)) {
                return false;
            }

            // 拡張子が ".txt" か確認
            if (!inputs[1].toLowerCase().endsWith(".txt") || !inputs[2].toLowerCase().endsWith(".txt")) {
                return false;
            }

            return true;
        }

        if (inputsType == 1) {
        }

        if (inputsType == 2) {
        }

        if (inputsType == 3) {
        }

        return false;
    }

}

// 以下のコマンドとその機能を提供する file_manipulator.py という Python
// スクリプトを作成してください。引数の入力が正しいかどうかをチェックするバリデータを必ず記述しましょう。

// ・reverse inputpath outputpath: inputpath にあるファイルを受け取り、outputpath に inputpath
// の内容を逆にした新しいファイルを作成します。
// ・copy inputpath outputpath: inputpath にあるファイルのコピーを作成し、outputpath として保存します。
// ・duplicate-contents inputpath n: inputpath にあるファイルの内容を読み込み、その内容を複製し、複製された内容を
// inputpath に n 回複製します。
// ・replace-string inputpath needle newstring: inputpath にあるファイルの内容から文字列
// 'needle' を検索し、'needle' の全てを 'newstring' に置き換えます。