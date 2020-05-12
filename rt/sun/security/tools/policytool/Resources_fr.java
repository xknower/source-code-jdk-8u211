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
/*     */ public class Resources_fr
/*     */   extends ListResourceBundle
/*     */ {
/*  35 */   private static final Object[][] contents = new Object[][] { { "NEWLINE", "\n" }, { "Warning.A.public.key.for.alias.signers.i.does.not.exist.Make.sure.a.KeyStore.is.properly.configured.", "Avertissement : il n''existe pas de clé publique pour l''alias {0}. Vérifiez que le fichier de clés d''accès est correctement configuré." }, { "Warning.Class.not.found.class", "Avertissement : classe introuvable - {0}" }, { "Warning.Invalid.argument.s.for.constructor.arg", "Avertissement : arguments non valides pour le constructeur - {0}" }, { "Illegal.Principal.Type.type", "Type de principal non admis : {0}" }, { "Illegal.option.option", "Option non admise : {0}" }, { "Usage.policytool.options.", "Syntaxe : policytool [options]" }, { ".file.file.policy.file.location", "  [-file <file>]    emplacement du fichier de règles" }, { "New", "&Nouveau" }, { "Open", "&Ouvrir..." }, { "Save", "Enregi&strer" }, { "Save.As", "Enregistrer so&us..." }, { "View.Warning.Log", "Affic&her le journal des avertissements" }, { "Exit", "&Quitter" }, { "Add.Policy.Entry", "&Ajouter une règle" }, { "Edit.Policy.Entry", "Modifi&er une règle" }, { "Remove.Policy.Entry", "Enlever une &règle" }, { "Edit", "Modifi&er" }, { "Retain", "Conserver" }, { "Warning.File.name.may.include.escaped.backslash.characters.It.is.not.necessary.to.escape.backslash.characters.the.tool.escapes", "Avertissement : il se peut que le nom de fichier contienne des barres obliques inverses avec caractère d'échappement. Il n'est pas nécessaire d'ajouter un caractère d'échappement aux barres obliques inverses. L'outil procède à l'échappement si nécessaire lorsqu'il écrit le contenu des règles dans la zone de stockage persistant.\n\nCliquez sur Conserver pour garder le nom saisi ou sur Modifier pour le remplacer." }, { "Add.Public.Key.Alias", "Ajouter un alias de clé publique" }, { "Remove.Public.Key.Alias", "Enlever un alias de clé publique" }, { "File", "&Fichier" }, { "KeyStore", "Fichier &de clés" }, { "Policy.File.", "Fichier de règles :" }, { "Could.not.open.policy.file.policyFile.e.toString.", "Impossible d''ouvrir le fichier de règles : {0}: {1}" }, { "Policy.Tool", "Policy Tool" }, { "Errors.have.occurred.while.opening.the.policy.configuration.View.the.Warning.Log.for.more.information.", "Des erreurs se sont produites à l'ouverture de la configuration de règles. Pour plus d'informations, consultez le journal des avertissements." }, { "Error", "Erreur" }, { "OK", "OK" }, { "Status", "Statut" }, { "Warning", "Avertissement" }, { "Permission.", "Droit :                                                       " }, { "Principal.Type.", "Type de principal :" }, { "Principal.Name.", "Nom de principal :" }, { "Target.Name.", "Nom de cible :                                                    " }, { "Actions.", "Actions :                                                             " }, { "OK.to.overwrite.existing.file.filename.", "Remplacer le fichier existant {0} ?" }, { "Cancel", "Annuler" }, { "CodeBase.", "Base de &code :" }, { "SignedBy.", "&Signé par :" }, { "Add.Principal", "&Ajouter un principal" }, { "Edit.Principal", "Modifi&er un principal" }, { "Remove.Principal", "Enleve&r un principal" }, { "Principals.", "&Principaux :" }, { ".Add.Permission", "  Ajouter un &droit" }, { ".Edit.Permission", "  Mod&ifier un droit" }, { "Remove.Permission", "Enlever un dr&oit" }, { "Done", "Terminé" }, { "KeyStore.URL.", "&URL du fichier de clés :" }, { "KeyStore.Type.", "&Type du fichier de clés :" }, { "KeyStore.Provider.", "Four&nisseur du fichier de clés :" }, { "KeyStore.Password.URL.", "UR&L du mot de passe du fichier de clés :" }, { "Principals", "Principaux" }, { ".Edit.Principal.", "  Modifier un principal :" }, { ".Add.New.Principal.", "  Ajouter un principal :" }, { "Permissions", "Droits" }, { ".Edit.Permission.", "  Modifier un droit :" }, { ".Add.New.Permission.", "  Ajouter un droit :" }, { "Signed.By.", "Signé par :" }, { "Cannot.Specify.Principal.with.a.Wildcard.Class.without.a.Wildcard.Name", "Impossible de spécifier un principal avec une classe générique sans nom générique" }, { "Cannot.Specify.Principal.without.a.Name", "Impossible de spécifier un principal sans nom" }, { "Permission.and.Target.Name.must.have.a.value", "Le droit et le nom de cible doivent avoir une valeur" }, { "Remove.this.Policy.Entry.", "Enlever cette règle ?" }, { "Overwrite.File", "Remplacer le fichier" }, { "Policy.successfully.written.to.filename", "Règle écrite dans {0}" }, { "null.filename", "nom de fichier NULL" }, { "Save.changes.", "Enregistrer les modifications ?" }, { "Yes", "&Oui" }, { "No", "&Non" }, { "Policy.Entry", "Règle" }, { "Save.Changes", "Enregistrer les modifications" }, { "No.Policy.Entry.selected", "Aucune règle sélectionnée" }, { "Unable.to.open.KeyStore.ex.toString.", "Impossible d''ouvrir le fichier de clés d''accès : {0}" }, { "No.principal.selected", "Aucun principal sélectionné" }, { "No.permission.selected", "Aucun droit sélectionné" }, { "name", "nom" }, { "configuration.type", "type de configuration" }, { "environment.variable.name", "Nom de variable d'environnement" }, { "library.name", "nom de bibliothèque" }, { "package.name", "nom de package" }, { "policy.type", "type de règle" }, { "property.name", "nom de propriété" }, { "provider.name", "nom du fournisseur" }, { "url", "url" }, { "method.list", "liste des méthodes" }, { "request.headers.list", "liste des en-têtes de demande" }, { "Principal.List", "Liste de principaux" }, { "Permission.List", "Liste de droits" }, { "Code.Base", "Base de code" }, { "KeyStore.U.R.L.", "URL du fichier de clés :" }, { "KeyStore.Password.U.R.L.", "URL du mot de passe du fichier de clés :" } };
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


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\tools\policytool\Resources_fr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */