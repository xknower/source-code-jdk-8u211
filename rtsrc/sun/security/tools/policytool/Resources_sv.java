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
/*     */ public class Resources_sv
/*     */   extends ListResourceBundle
/*     */ {
/*  35 */   private static final Object[][] contents = new Object[][] { { "NEWLINE", "\n" }, { "Warning.A.public.key.for.alias.signers.i.does.not.exist.Make.sure.a.KeyStore.is.properly.configured.", "Varning! Det finns ingen öppen nyckel för aliaset {0}. Kontrollera att det aktuella nyckellagret är korrekt konfigurerat." }, { "Warning.Class.not.found.class", "Varning! Klassen hittades inte: {0}" }, { "Warning.Invalid.argument.s.for.constructor.arg", "Varning! Ogiltiga argument för konstruktor: {0}" }, { "Illegal.Principal.Type.type", "Otillåten identitetshavaretyp: {0}" }, { "Illegal.option.option", "Otillåtet alternativ: {0}" }, { "Usage.policytool.options.", "Syntax: policytool [alternativ]" }, { ".file.file.policy.file.location", "  [-file <fil>]    policyfiladress" }, { "New", "&Nytt" }, { "Open", "&Öppna..." }, { "Save", "S&para" }, { "Save.As", "Spara &som..." }, { "View.Warning.Log", "Visa varningslo&gg" }, { "Exit", "A&vsluta" }, { "Add.Policy.Entry", "&Lägg till policypost" }, { "Edit.Policy.Entry", "&Redigera policypost" }, { "Remove.Policy.Entry", "&Ta bort policypost" }, { "Edit", "&Redigera" }, { "Retain", "Behåll" }, { "Warning.File.name.may.include.escaped.backslash.characters.It.is.not.necessary.to.escape.backslash.characters.the.tool.escapes", "Varning! Filnamnet kan innehålla omvända snedstreck inom citattecken. Citattecken krävs inte för omvända snedstreck (verktyget hanterar detta när policyinnehållet skrivs till det beständiga lagret).\n\nKlicka på Behåll för att behålla det angivna namnet, eller klicka på Redigera för att ändra det." }, { "Add.Public.Key.Alias", "Lägg till alias till öppen nyckel" }, { "Remove.Public.Key.Alias", "Ta bort alias för öppen nyckel" }, { "File", "&Arkiv" }, { "KeyStore", "&KeyStore" }, { "Policy.File.", "Policyfil:" }, { "Could.not.open.policy.file.policyFile.e.toString.", "Kan inte öppna policyfilen: {0}: {1}" }, { "Policy.Tool", "Policyverktyg" }, { "Errors.have.occurred.while.opening.the.policy.configuration.View.the.Warning.Log.for.more.information.", "Ett fel inträffade när policykonfigurationen skulle öppnas. Se varningsloggen för mer information." }, { "Error", "Fel" }, { "OK", "OK" }, { "Status", "Status" }, { "Warning", "Varning" }, { "Permission.", "Behörighet:                                                       " }, { "Principal.Type.", "Identitetshavaretyp:" }, { "Principal.Name.", "Identitetshavare:" }, { "Target.Name.", "Mål:                                                    " }, { "Actions.", "Åtgärder:                                                             " }, { "OK.to.overwrite.existing.file.filename.", "Ska den befintliga filen {0} skrivas över?" }, { "Cancel", "Avbryt" }, { "CodeBase.", "&CodeBase:" }, { "SignedBy.", "&SignedBy:" }, { "Add.Principal", "&Lägg till identitetshavare" }, { "Edit.Principal", "&Redigera identitetshavare" }, { "Remove.Principal", "&Ta bort identitetshavare" }, { "Principals.", "Identitetshavare:" }, { ".Add.Permission", "  L&ägg till behörighet" }, { ".Edit.Permission", "  Re&digera behörighet" }, { "Remove.Permission", "Ta &bort behörighet" }, { "Done", "Utförd" }, { "KeyStore.URL.", "Nyckellager-&URL:" }, { "KeyStore.Type.", "Nyckellager&typ:" }, { "KeyStore.Provider.", "Nyckellager&leverantör:" }, { "KeyStore.Password.URL.", "Lösen&ords-URL till nyckellager:" }, { "Principals", "Identitetshavare" }, { ".Edit.Principal.", "  Redigera identitetshavare:" }, { ".Add.New.Principal.", "  Lägg till ny identitetshavare:" }, { "Permissions", "Behörigheter" }, { ".Edit.Permission.", "  Redigera behörighet:" }, { ".Add.New.Permission.", "  Lägg till ny behörighet:" }, { "Signed.By.", "Signerad av:" }, { "Cannot.Specify.Principal.with.a.Wildcard.Class.without.a.Wildcard.Name", "Kan inte specificera identitetshavare med jokerteckenklass utan jokerteckennamn" }, { "Cannot.Specify.Principal.without.a.Name", "Kan inte specificera identitetshavare utan namn" }, { "Permission.and.Target.Name.must.have.a.value", "Behörighet och målnamn måste ha ett värde" }, { "Remove.this.Policy.Entry.", "Vill du ta bort policyposten?" }, { "Overwrite.File", "Skriv över fil" }, { "Policy.successfully.written.to.filename", "Policy har skrivits till {0}" }, { "null.filename", "nullfilnamn" }, { "Save.changes.", "Vill du spara ändringarna?" }, { "Yes", "&Ja" }, { "No", "&Nej" }, { "Policy.Entry", "Policyfel" }, { "Save.Changes", "Spara ändringar" }, { "No.Policy.Entry.selected", "Ingen policypost har valts" }, { "Unable.to.open.KeyStore.ex.toString.", "Kan inte öppna nyckellagret: {0}" }, { "No.principal.selected", "Ingen identitetshavare har valts" }, { "No.permission.selected", "Ingen behörighet har valts" }, { "name", "namn" }, { "configuration.type", "konfigurationstyp" }, { "environment.variable.name", "variabelnamn för miljö" }, { "library.name", "biblioteksnamn" }, { "package.name", "paketnamn" }, { "policy.type", "policytyp" }, { "property.name", "egenskapsnamn" }, { "provider.name", "leverantörsnamn" }, { "url", "url" }, { "method.list", "metodlista" }, { "request.headers.list", "lista över begärandehuvuden" }, { "Principal.List", "Lista över identitetshavare" }, { "Permission.List", "Behörighetslista" }, { "Code.Base", "Kodbas" }, { "KeyStore.U.R.L.", "URL för nyckellager:" }, { "KeyStore.Password.U.R.L.", "URL för lösenord till nyckellager:" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\tools\policytool\Resources_sv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */