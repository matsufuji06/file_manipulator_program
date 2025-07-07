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
    /**
     * 定数
     */
    public static final String[] INPUTS = {
            "reverse",
            "copy",
            "duplicate-contents",
            "replace-string"
    };

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
                System.exit(1);
            }
            try {
                execReverse(args[1], args[2]);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ファイルの入出力に失敗しました。");
                System.exit(1);
            }
        }

        if (inputsType == 1) {
            if (!isValidFile(args, inputsType)) {
                System.err.println("格納ファイルが不正です。");
                System.exit(1);
            }
            try {
                execCopy(args[1], args[2]);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ファイルの入出力に失敗しました。");
                System.exit(1);
            }
        }

        if (inputsType == 2) {
            if (!isValidFile(args, inputsType)) {
                System.err.println("格納ファイルが不正です。");
                System.exit(1);
            }
            if (!isNumber(args[2])) {
                System.err.println("指定した複製回数nが不正です。");
                System.exit(1);
            }
            try {
                execDuplicateContents(args[1], args[1], Integer.parseInt(args[2]));

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ファイルの入出力に失敗しました。");
                System.exit(1);
            }
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
    private static void execReverse(String inputPath, String outputPath) throws IOException {
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
        writeOutput(newArrayList, outputPath, false);
    }

    /**
     * 指定された入力ファイルの内容を、出力ファイルにコピーする *
     * 
     * @param inputPath
     * @param outputPath
     */
    private static void execCopy(String inputPath, String outputPath) throws IOException {
        ArrayList<String> newArrayList = new ArrayList<>();

        try {
            // inputを読み込み、copyするところまで
            BufferedReader reader = new BufferedReader(new FileReader(inputPath));

            ArrayList<String> tmpArrayList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                tmpArrayList.add(line);
            }

            for (var el : tmpArrayList) {
                newArrayList.add(el);
            }

        } catch (IOException e) {
            System.err.println("読み込み失敗: " + e.getMessage());
        }

        // inputをoutputに書き込みまで
        writeOutput(newArrayList, outputPath, false);
    }

    /**
     * 指定された入力ファイルの内容を、出力ファイルにコピーする *
     * 
     * @param inputPath
     * @param outputPath
     * @param copyCount
     */
    private static void execDuplicateContents(String inputPath, String outputPath, int copyCount) throws IOException {
        ArrayList<String> newArrayList = new ArrayList<>();

        try {
            // inputを読み込み、copyするところまで
            BufferedReader reader = new BufferedReader(new FileReader(inputPath));

            ArrayList<String> tmpArrayList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                tmpArrayList.add(line);
            }

            for (var el : tmpArrayList) {
                newArrayList.add(el);
            }

        } catch (IOException e) {
            System.err.println("読み込み失敗: " + e.getMessage());
        }

        // ファイルの内容をリセットする
        resetFile(outputPath);

        // inputをoutputに書き込みまで
        for (var i = 0; i < copyCount; i++) {
            writeOutput(newArrayList, outputPath, true);
        }
    }

    /**
     * 出力ファイルに１行ずつ書き込む
     * 
     * @param list
     * @param filePath
     * @param shouldAppend
     */
    private static void writeOutput(ArrayList<String> list, String filePath, boolean shouldAppend) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, shouldAppend))) {
            for (var txt : list) {
                writer.write(txt);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("書き込み失敗: " + e.getMessage());
        }
    }

    /**
     * ファイルの内容をリセットする
     * 
     * @param filePath
     */
    private static void resetFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // 何も書かないことでファイルの内容を空にする
            System.out.println("ファイルの内容をリセットしました。");
        } catch (IOException e) {
            e.printStackTrace();
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
        if (inputsType == 0 || inputsType == 1) {
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

        if (inputsType == 2) {
            inputPath = Paths.get(inputs[1]);

            // パスが正しくパースできるか、存在するかをチェック
            if (!Files.exists(inputPath)) {
                return false;
            }

            // ファイルかどうか（ディレクトリではない）もチェック
            if (!Files.isRegularFile(inputPath)) {
                return false;
            }

            // 拡張子が ".txt" か確認
            if (!inputs[1].toLowerCase().endsWith(".txt")) {
                return false;
            }

            return true;
        }

        if (inputsType == 3) {
        }

        return false;
    }

    /**
     * 渡した引数が整数か判定する
     * 
     * @param str
     * @return
     */
    private static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
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