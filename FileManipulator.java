import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManipulator {
    public static final String[] INPUTS = { "reverse", "copy", "duplicate-contents", "replace-string" };

    public static void main(String[] args) {
        if (!isValidInputsPrompt(args[0])) {
            System.err.println("プロンプト（第一引数）の値が不正です。");
            System.exit(1); // エラー終了
        }

        if (!isValidInputsCount(args)) {
            System.err.println("コマンド引数の数が不正です。");
            System.exit(1); // エラー終了
        }
    }

    private static boolean isValidInputsPrompt(String prompt) {
        if (Arrays.asList(INPUTS).contains(prompt)) {
            return true;
        }
        return false;
    }

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

    private static boolean isValidInputsType(String[] inputs) {
        int inputsType = switch (inputs[0]) {
            case "reverse", "copy" -> 0;
            case "duplicate-contents" -> 1;
            case "replace-string" -> 2;
            default -> -1;
        };

        try {
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

                // 任意：拡張子が ".txt" か確認（必要に応じて）
                if (!inputs[1].toLowerCase().endsWith(".txt") || !inputs[2].toLowerCase().endsWith(".txt")) {
                    return false;
                }

                return true;
            }

            if (inputsType == 1) {

            }

            if (inputsType == 2) {

            }

            return false;

        } catch (Exception e) {
            // 無効なパスの場合
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