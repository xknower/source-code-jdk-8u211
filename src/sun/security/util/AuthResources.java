/*     */ package sun.security.util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthResources
/*     */   extends ListResourceBundle
/*     */ {
/*  40 */   private static final Object[][] contents = new Object[][] { { "invalid.null.input.value", "invalid null input: {0}" }, { "NTDomainPrincipal.name", "NTDomainPrincipal: {0}" }, { "NTNumericCredential.name", "NTNumericCredential: {0}" }, { "Invalid.NTSid.value", "Invalid NTSid value" }, { "NTSid.name", "NTSid: {0}" }, { "NTSidDomainPrincipal.name", "NTSidDomainPrincipal: {0}" }, { "NTSidGroupPrincipal.name", "NTSidGroupPrincipal: {0}" }, { "NTSidPrimaryGroupPrincipal.name", "NTSidPrimaryGroupPrincipal: {0}" }, { "NTSidUserPrincipal.name", "NTSidUserPrincipal: {0}" }, { "NTUserPrincipal.name", "NTUserPrincipal: {0}" }, { "UnixNumericGroupPrincipal.Primary.Group.name", "UnixNumericGroupPrincipal [Primary Group]: {0}" }, { "UnixNumericGroupPrincipal.Supplementary.Group.name", "UnixNumericGroupPrincipal [Supplementary Group]: {0}" }, { "UnixNumericUserPrincipal.name", "UnixNumericUserPrincipal: {0}" }, { "UnixPrincipal.name", "UnixPrincipal: {0}" }, { "Unable.to.properly.expand.config", "Unable to properly expand {0}" }, { "extra.config.No.such.file.or.directory.", "{0} (No such file or directory)" }, { "Configuration.Error.No.such.file.or.directory", "Configuration Error:\n\tNo such file or directory" }, { "Configuration.Error.Invalid.control.flag.flag", "Configuration Error:\n\tInvalid control flag, {0}" }, { "Configuration.Error.Can.not.specify.multiple.entries.for.appName", "Configuration Error:\n\tCan not specify multiple entries for {0}" }, { "Configuration.Error.expected.expect.read.end.of.file.", "Configuration Error:\n\texpected [{0}], read [end of file]" }, { "Configuration.Error.Line.line.expected.expect.found.value.", "Configuration Error:\n\tLine {0}: expected [{1}], found [{2}]" }, { "Configuration.Error.Line.line.expected.expect.", "Configuration Error:\n\tLine {0}: expected [{1}]" }, { "Configuration.Error.Line.line.system.property.value.expanded.to.empty.value", "Configuration Error:\n\tLine {0}: system property [{1}] expanded to empty value" }, { "username.", "username: " }, { "password.", "password: " }, { "Please.enter.keystore.information", "Please enter keystore information" }, { "Keystore.alias.", "Keystore alias: " }, { "Keystore.password.", "Keystore password: " }, { "Private.key.password.optional.", "Private key password (optional): " }, { "Kerberos.username.defUsername.", "Kerberos username [{0}]: " }, { "Kerberos.password.for.username.", "Kerberos password for {0}: " }, { ".error.parsing.", ": error parsing " }, { "COLON", ": " }, { ".error.adding.Permission.", ": error adding Permission " }, { "SPACE", " " }, { ".error.adding.Entry.", ": error adding Entry " }, { "LPARAM", "(" }, { "RPARAM", ")" }, { "attempt.to.add.a.Permission.to.a.readonly.PermissionCollection", "attempt to add a Permission to a readonly PermissionCollection" }, { "expected.keystore.type", "expected keystore type" }, { "can.not.specify.Principal.with.a.wildcard.class.without.a.wildcard.name", "can not specify Principal with a wildcard class without a wildcard name" }, { "expected.codeBase.or.SignedBy", "expected codeBase or SignedBy" }, { "only.Principal.based.grant.entries.permitted", "only Principal-based grant entries permitted" }, { "expected.permission.entry", "expected permission entry" }, { "number.", "number " }, { "expected.expect.read.end.of.file.", "expected {0}, read end of file" }, { "expected.read.end.of.file", "expected ';', read end of file" }, { "line.", "line " }, { ".expected.", ": expected '" }, { ".found.", "', found '" }, { "QUOTE", "'" }, { "SolarisNumericGroupPrincipal.Primary.Group.", "SolarisNumericGroupPrincipal [Primary Group]: " }, { "SolarisNumericGroupPrincipal.Supplementary.Group.", "SolarisNumericGroupPrincipal [Supplementary Group]: " }, { "SolarisNumericUserPrincipal.", "SolarisNumericUserPrincipal: " }, { "SolarisPrincipal.", "SolarisPrincipal: " }, { "provided.null.name", "provided null name" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 152 */     return contents;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\securit\\util\AuthResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */