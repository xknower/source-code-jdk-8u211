/*     */ package sun.security.tools.policytool;
/*     */ 
/*     */ import java.util.ListResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Resources_ja
/*     */   extends ListResourceBundle
/*     */ {
/*  35 */   private static final Object[][] contents = new Object[][] { { "NEWLINE", "\n" }, { "Warning.A.public.key.for.alias.signers.i.does.not.exist.Make.sure.a.KeyStore.is.properly.configured.", "警告: 別名{0}の公開キーが存在しません。キーストアが正しく構成されていることを確認してください。" }, { "Warning.Class.not.found.class", "警告: クラスが見つかりません: {0}" }, { "Warning.Invalid.argument.s.for.constructor.arg", "警告: コンストラクタの引数が無効です: {0}" }, { "Illegal.Principal.Type.type", "不正なプリンシパルのタイプ: {0}" }, { "Illegal.option.option", "不正なオプション: {0}" }, { "Usage.policytool.options.", "使用方法: policytool [options]" }, { ".file.file.policy.file.location", "  [-file <file>]  ポリシー・ファイルの場所" }, { "New", "新規(&N)" }, { "Open", "開く(&O)..." }, { "Save", "保存(&S)" }, { "Save.As", "別名保存(&A)..." }, { "View.Warning.Log", "警告ログの表示(&W)" }, { "Exit", "終了(&X)" }, { "Add.Policy.Entry", "ポリシー・エントリの追加(&A)" }, { "Edit.Policy.Entry", "ポリシー・エントリの編集(&E)" }, { "Remove.Policy.Entry", "ポリシー・エントリの削除(&R)" }, { "Edit", "編集(&E)" }, { "Retain", "保持" }, { "Warning.File.name.may.include.escaped.backslash.characters.It.is.not.necessary.to.escape.backslash.characters.the.tool.escapes", "警告: ファイル名にエスケープされたバックスラッシュ文字が含まれている可能性があります。バックスラッシュ文字をエスケープする必要はありません(ツールはポリシー内容を永続ストアに書き込むときに、必要に応じて文字をエスケープします)。\n\n入力済の名前を保持するには「保持」をクリックし、名前を編集するには「編集」をクリックしてください。" }, { "Add.Public.Key.Alias", "公開キーの別名の追加" }, { "Remove.Public.Key.Alias", "公開キーの別名を削除" }, { "File", "ファイル(&F)" }, { "KeyStore", "キーストア(&K)" }, { "Policy.File.", "ポリシー・ファイル:" }, { "Could.not.open.policy.file.policyFile.e.toString.", "ポリシー・ファイルを開けませんでした: {0}: {1}" }, { "Policy.Tool", "ポリシー・ツール" }, { "Errors.have.occurred.while.opening.the.policy.configuration.View.the.Warning.Log.for.more.information.", "ポリシー構成を開くときにエラーが発生しました。詳細は警告ログを参照してください。" }, { "Error", "エラー" }, { "OK", "OK" }, { "Status", "状態" }, { "Warning", "警告" }, { "Permission.", "アクセス権:                                                       " }, { "Principal.Type.", "プリンシパルのタイプ:" }, { "Principal.Name.", "プリンシパルの名前:" }, { "Target.Name.", "ターゲット名:                                                    " }, { "Actions.", "アクション:                                                             " }, { "OK.to.overwrite.existing.file.filename.", "既存のファイル{0}に上書きしますか。" }, { "Cancel", "取消" }, { "CodeBase.", "CodeBase(&C):" }, { "SignedBy.", "SignedBy(&S):" }, { "Add.Principal", "プリンシパルの追加(&A)" }, { "Edit.Principal", "プリンシパルの編集(&E)" }, { "Remove.Principal", "プリンシパルの削除(&R)" }, { "Principals.", "プリンシパル(&P):" }, { ".Add.Permission", "  アクセス権の追加(&D)" }, { ".Edit.Permission", "  アクセス権の編集(&I)" }, { "Remove.Permission", "アクセス権の削除(&M)" }, { "Done", "完了" }, { "KeyStore.URL.", "キーストアURL(&U):" }, { "KeyStore.Type.", "キーストアのタイプ(&T):" }, { "KeyStore.Provider.", "キーストア・プロバイダ(&P):" }, { "KeyStore.Password.URL.", "キーストア・パスワードURL(&W):" }, { "Principals", "プリンシパル" }, { ".Edit.Principal.", "  プリンシパルの編集:" }, { ".Add.New.Principal.", "  プリンシパルの新規追加:" }, { "Permissions", "アクセス権" }, { ".Edit.Permission.", "  アクセス権の編集:" }, { ".Add.New.Permission.", "  新規アクセス権の追加:" }, { "Signed.By.", "署名者:" }, { "Cannot.Specify.Principal.with.a.Wildcard.Class.without.a.Wildcard.Name", "ワイルドカード名のないワイルドカード・クラスを使用してプリンシパルを指定することはできません" }, { "Cannot.Specify.Principal.without.a.Name", "名前を使用せずにプリンシパルを指定することはできません" }, { "Permission.and.Target.Name.must.have.a.value", "アクセス権とターゲット名は、値を保持する必要があります" }, { "Remove.this.Policy.Entry.", "このポリシー・エントリを削除しますか。" }, { "Overwrite.File", "ファイルを上書きします" }, { "Policy.successfully.written.to.filename", "ポリシーの{0}への書込みに成功しました" }, { "null.filename", "ファイル名がnullです" }, { "Save.changes.", "変更を保存しますか。" }, { "Yes", "はい(&Y)" }, { "No", "いいえ(&N)" }, { "Policy.Entry", "ポリシー・エントリ" }, { "Save.Changes", "変更を保存します" }, { "No.Policy.Entry.selected", "ポリシー・エントリが選択されていません" }, { "Unable.to.open.KeyStore.ex.toString.", "キーストア{0}を開けません" }, { "No.principal.selected", "プリンシパルが選択されていません" }, { "No.permission.selected", "アクセス権が選択されていません" }, { "name", "名前" }, { "configuration.type", "構成タイプ" }, { "environment.variable.name", "環境変数名" }, { "library.name", "ライブラリ名" }, { "package.name", "パッケージ名" }, { "policy.type", "ポリシー・タイプ" }, { "property.name", "プロパティ名" }, { "provider.name", "プロバイダ名" }, { "url", "URL" }, { "method.list", "メソッド・リスト" }, { "request.headers.list", "リクエスト・ヘッダー・リスト" }, { "Principal.List", "プリンシパルのリスト" }, { "Permission.List", "アクセス権のリスト" }, { "Code.Base", "コード・ベース" }, { "KeyStore.U.R.L.", "キーストアU R L:" }, { "KeyStore.Password.U.R.L.", "キーストア・パスワードU R L:" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[][] getContents() {
/* 157 */     return contents;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\tools\policytool\Resources_ja.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */