/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMErrorImpl;
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMLocatorImpl;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.DOMError;
/*     */ import org.w3c.dom.DOMErrorHandler;
/*     */ import org.w3c.dom.DOMLocator;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMErrorHandlerWrapper
/*     */   implements XMLErrorHandler, DOMErrorHandler
/*     */ {
/*     */   protected DOMErrorHandler fDomErrorHandler;
/*     */   boolean eStatus = true;
/*     */   protected PrintWriter fOut;
/*     */   public Node fCurrentNode;
/*  72 */   protected final XMLErrorCode fErrorCode = new XMLErrorCode(null, null);
/*     */   
/*  74 */   protected final DOMErrorImpl fDOMError = new DOMErrorImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMErrorHandlerWrapper() {
/*  85 */     this.fOut = new PrintWriter(System.err);
/*     */   }
/*     */ 
/*     */   
/*     */   public DOMErrorHandlerWrapper(DOMErrorHandler domErrorHandler) {
/*  90 */     this.fDomErrorHandler = domErrorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(DOMErrorHandler errorHandler) {
/* 100 */     this.fDomErrorHandler = errorHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public DOMErrorHandler getErrorHandler() {
/* 105 */     return this.fDomErrorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warning(String domain, String key, XMLParseException exception) throws XNIException {
/* 131 */     this.fDOMError.fSeverity = 1;
/* 132 */     this.fDOMError.fException = exception;
/*     */     
/* 134 */     this.fDOMError.fType = key;
/* 135 */     this.fDOMError.fRelatedData = this.fDOMError.fMessage = exception.getMessage();
/* 136 */     DOMLocatorImpl locator = this.fDOMError.fLocator;
/* 137 */     if (locator != null) {
/* 138 */       locator.fColumnNumber = exception.getColumnNumber();
/* 139 */       locator.fLineNumber = exception.getLineNumber();
/* 140 */       locator.fUtf16Offset = exception.getCharacterOffset();
/* 141 */       locator.fUri = exception.getExpandedSystemId();
/* 142 */       locator.fRelatedNode = this.fCurrentNode;
/*     */     } 
/* 144 */     if (this.fDomErrorHandler != null) {
/* 145 */       this.fDomErrorHandler.handleError(this.fDOMError);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(String domain, String key, XMLParseException exception) throws XNIException {
/* 167 */     this.fDOMError.fSeverity = 2;
/* 168 */     this.fDOMError.fException = exception;
/*     */     
/* 170 */     this.fDOMError.fType = key;
/* 171 */     this.fDOMError.fRelatedData = this.fDOMError.fMessage = exception.getMessage();
/* 172 */     DOMLocatorImpl locator = this.fDOMError.fLocator;
/* 173 */     if (locator != null) {
/* 174 */       locator.fColumnNumber = exception.getColumnNumber();
/* 175 */       locator.fLineNumber = exception.getLineNumber();
/* 176 */       locator.fUtf16Offset = exception.getCharacterOffset();
/* 177 */       locator.fUri = exception.getExpandedSystemId();
/* 178 */       locator.fRelatedNode = this.fCurrentNode;
/*     */     } 
/* 180 */     if (this.fDomErrorHandler != null) {
/* 181 */       this.fDomErrorHandler.handleError(this.fDOMError);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fatalError(String domain, String key, XMLParseException exception) throws XNIException {
/* 211 */     this.fDOMError.fSeverity = 3;
/* 212 */     this.fDOMError.fException = exception;
/* 213 */     this.fErrorCode.setValues(domain, key);
/* 214 */     String domErrorType = DOMErrorTypeMap.getDOMErrorType(this.fErrorCode);
/* 215 */     this.fDOMError.fType = (domErrorType != null) ? domErrorType : key;
/* 216 */     this.fDOMError.fRelatedData = this.fDOMError.fMessage = exception.getMessage();
/* 217 */     DOMLocatorImpl locator = this.fDOMError.fLocator;
/* 218 */     if (locator != null) {
/* 219 */       locator.fColumnNumber = exception.getColumnNumber();
/* 220 */       locator.fLineNumber = exception.getLineNumber();
/* 221 */       locator.fUtf16Offset = exception.getCharacterOffset();
/* 222 */       locator.fUri = exception.getExpandedSystemId();
/* 223 */       locator.fRelatedNode = this.fCurrentNode;
/*     */     } 
/* 225 */     if (this.fDomErrorHandler != null) {
/* 226 */       this.fDomErrorHandler.handleError(this.fDOMError);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleError(DOMError error) {
/* 232 */     printError(error);
/* 233 */     return this.eStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void printError(DOMError error) {
/* 239 */     int severity = error.getSeverity();
/* 240 */     this.fOut.print("[");
/* 241 */     if (severity == 1) {
/* 242 */       this.fOut.print("Warning");
/* 243 */     } else if (severity == 2) {
/* 244 */       this.fOut.print("Error");
/*     */     } else {
/* 246 */       this.fOut.print("FatalError");
/* 247 */       this.eStatus = false;
/*     */     } 
/* 249 */     this.fOut.print("] ");
/* 250 */     DOMLocator locator = error.getLocation();
/* 251 */     if (locator != null) {
/* 252 */       this.fOut.print(locator.getLineNumber());
/* 253 */       this.fOut.print(":");
/* 254 */       this.fOut.print(locator.getColumnNumber());
/* 255 */       this.fOut.print(":");
/* 256 */       this.fOut.print(locator.getByteOffset());
/* 257 */       this.fOut.print(",");
/* 258 */       this.fOut.print(locator.getUtf16Offset());
/* 259 */       Node node = locator.getRelatedNode();
/* 260 */       if (node != null) {
/* 261 */         this.fOut.print("[");
/* 262 */         this.fOut.print(node.getNodeName());
/* 263 */         this.fOut.print("]");
/*     */       } 
/* 265 */       String systemId = locator.getUri();
/* 266 */       if (systemId != null) {
/* 267 */         int index = systemId.lastIndexOf('/');
/* 268 */         if (index != -1)
/* 269 */           systemId = systemId.substring(index + 1); 
/* 270 */         this.fOut.print(": ");
/* 271 */         this.fOut.print(systemId);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 276 */     this.fOut.print(":");
/* 277 */     this.fOut.print(error.getMessage());
/* 278 */     this.fOut.println();
/* 279 */     this.fOut.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DOMErrorTypeMap
/*     */   {
/*     */     private static final Map<XMLErrorCode, String> fgDOMErrorTypeTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 297 */       Map<XMLErrorCode, String> aDOMErrorTypeTable = new HashMap<>();
/* 298 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect"), "wf-invalid-character");
/* 299 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent"), "wf-invalid-character");
/* 300 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "TwoColonsInQName"), "wf-invalid-character-in-node-name");
/* 301 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ColonNotLegalWithNS"), "wf-invalid-character-in-node-name");
/* 302 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInProlog"), "wf-invalid-character");
/*     */ 
/*     */       
/* 305 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent"), "wf-invalid-character");
/* 306 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "CDSectUnterminated"), "wf-invalid-character");
/* 307 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "DoctypeNotAllowed"), "doctype-not-allowed");
/* 308 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ETagRequired"), "wf-invalid-character-in-node-name");
/* 309 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementUnterminated"), "wf-invalid-character-in-node-name");
/* 310 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EqRequiredInAttribute"), "wf-invalid-character");
/* 311 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "OpenQuoteExpected"), "wf-invalid-character");
/* 312 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "CloseQuoteExpected"), "wf-invalid-character");
/* 313 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ETagUnterminated"), "wf-invalid-character-in-node-name");
/* 314 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MarkupNotRecognizedInContent"), "wf-invalid-character");
/* 315 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "DoctypeIllegalInContent"), "doctype-not-allowed");
/* 316 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInAttValue"), "wf-invalid-character");
/* 317 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInPI"), "wf-invalid-character");
/* 318 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInInternalSubset"), "wf-invalid-character");
/* 319 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "QuoteRequiredInAttValue"), "wf-invalid-character");
/* 320 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "LessthanInAttValue"), "wf-invalid-character");
/* 321 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "AttributeValueUnterminated"), "wf-invalid-character");
/* 322 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PITargetRequired"), "wf-invalid-character");
/* 323 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "SpaceRequiredInPI"), "wf-invalid-character");
/* 324 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PIUnterminated"), "wf-invalid-character");
/* 325 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ReservedPITarget"), "wf-invalid-character");
/* 326 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PI_NOT_IN_ONE_ENTITY"), "wf-invalid-character");
/* 327 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PINotInOneEntity"), "wf-invalid-character");
/* 328 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid"), "unsupported-encoding");
/* 329 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported"), "unsupported-encoding");
/* 330 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInEntityValue"), "wf-invalid-character-in-node-name");
/* 331 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInExternalSubset"), "wf-invalid-character");
/* 332 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInIgnoreSect"), "wf-invalid-character");
/* 333 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInPublicID"), "wf-invalid-character");
/* 334 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInSystemID"), "wf-invalid-character");
/* 335 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "SpaceRequiredAfterSYSTEM"), "wf-invalid-character");
/* 336 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "QuoteRequiredInSystemID"), "wf-invalid-character");
/* 337 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "SystemIDUnterminated"), "wf-invalid-character");
/* 338 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "SpaceRequiredAfterPUBLIC"), "wf-invalid-character");
/* 339 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "QuoteRequiredInPublicID"), "wf-invalid-character");
/* 340 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PublicIDUnterminated"), "wf-invalid-character");
/* 341 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PubidCharIllegal"), "wf-invalid-character");
/* 342 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "SpaceRequiredBetweenPublicAndSystem"), "wf-invalid-character");
/* 343 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ROOT_ELEMENT_TYPE_IN_DOCTYPEDECL"), "wf-invalid-character-in-node-name");
/* 344 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ROOT_ELEMENT_TYPE_REQUIRED"), "wf-invalid-character-in-node-name");
/* 345 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "DoctypedeclUnterminated"), "wf-invalid-character-in-node-name");
/* 346 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PEReferenceWithinMarkup"), "wf-invalid-character");
/* 347 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_MARKUP_NOT_RECOGNIZED_IN_DTD"), "wf-invalid-character");
/* 348 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ELEMENTDECL"), "wf-invalid-character");
/* 349 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_TYPE_REQUIRED_IN_ELEMENTDECL"), "wf-invalid-character");
/* 350 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_CONTENTSPEC_IN_ELEMENTDECL"), "wf-invalid-character");
/* 351 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CONTENTSPEC_REQUIRED_IN_ELEMENTDECL"), "wf-invalid-character");
/* 352 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementDeclUnterminated"), "wf-invalid-character");
/* 353 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN"), "wf-invalid-character");
/* 354 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN"), "wf-invalid-character");
/* 355 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_TYPE_REQUIRED_IN_MIXED_CONTENT"), "wf-invalid-character");
/* 356 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CLOSE_PAREN_REQUIRED_IN_MIXED"), "wf-invalid-character");
/* 357 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MixedContentUnterminated"), "wf-invalid-character");
/* 358 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ATTLISTDECL"), "wf-invalid-character");
/* 359 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_TYPE_REQUIRED_IN_ATTLISTDECL"), "wf-invalid-character");
/* 360 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ATTRIBUTE_NAME_IN_ATTDEF"), "wf-invalid-character");
/* 361 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "AttNameRequiredInAttDef"), "wf-invalid-character");
/* 362 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ATTTYPE_IN_ATTDEF"), "wf-invalid-character");
/* 363 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "AttTypeRequiredInAttDef"), "wf-invalid-character");
/* 364 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_DEFAULTDECL_IN_ATTDEF"), "wf-invalid-character");
/* 365 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ATTRIBUTE_DEFINITION"), "wf-invalid-character");
/* 366 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_AFTER_NOTATION_IN_NOTATIONTYPE"), "wf-invalid-character");
/* 367 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_OPEN_PAREN_REQUIRED_IN_NOTATIONTYPE"), "wf-invalid-character");
/* 368 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NAME_REQUIRED_IN_NOTATIONTYPE"), "wf-invalid-character");
/* 369 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "NotationTypeUnterminated"), "wf-invalid-character");
/* 370 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NMTOKEN_REQUIRED_IN_ENUMERATION"), "wf-invalid-character");
/* 371 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EnumerationUnterminated"), "wf-invalid-character");
/* 372 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DISTINCT_TOKENS_IN_ENUMERATION"), "wf-invalid-character");
/* 373 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DISTINCT_NOTATION_IN_ENUMERATION"), "wf-invalid-character");
/* 374 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_AFTER_FIXED_IN_DEFAULTDECL"), "wf-invalid-character");
/* 375 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "IncludeSectUnterminated"), "wf-invalid-character");
/* 376 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "IgnoreSectUnterminated"), "wf-invalid-character");
/* 377 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "NameRequiredInPEReference"), "wf-invalid-character");
/* 378 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "SemicolonRequiredInPEReference"), "wf-invalid-character");
/* 379 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL"), "wf-invalid-character-in-node-name");
/* 380 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_PERCENT_IN_PEDECL"), "wf-invalid-character-in-node-name");
/* 381 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_PEDECL"), "wf-invalid-character-in-node-name");
/* 382 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ENTITY_NAME_REQUIRED_IN_ENTITYDECL"), "wf-invalid-character-in-node-name");
/* 383 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL"), "wf-invalid-character-in-node-name");
/* 384 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_UNPARSED_ENTITYDECL"), "wf-invalid-character");
/* 385 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_NDATA_IN_UNPARSED_ENTITYDECL"), "wf-invalid-character");
/* 386 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NOTATION_NAME_REQUIRED_FOR_UNPARSED_ENTITYDECL"), "wf-invalid-character");
/* 387 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityDeclUnterminated"), "wf-invalid-character-in-node-name");
/* 388 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION"), "wf-invalid-character-in-node-name");
/* 389 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ExternalIDRequired"), "wf-invalid-character");
/* 390 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_PUBIDLITERAL_IN_EXTERNALID"), "wf-invalid-character");
/* 391 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_AFTER_PUBIDLITERAL_IN_EXTERNALID"), "wf-invalid-character");
/* 392 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_SYSTEMLITERAL_IN_EXTERNALID"), "wf-invalid-character");
/* 393 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_URI_FRAGMENT_IN_SYSTEMID"), "wf-invalid-character");
/* 394 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_NOTATIONDECL"), "wf-invalid-character-in-node-name");
/* 395 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NOTATION_NAME_REQUIRED_IN_NOTATIONDECL"), "wf-invalid-character-in-node-name");
/* 396 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_SPACE_REQUIRED_AFTER_NOTATION_NAME_IN_NOTATIONDECL"), "wf-invalid-character-in-node-name");
/* 397 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ExternalIDorPublicIDRequired"), "wf-invalid-character");
/* 398 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "NotationDeclUnterminated"), "wf-invalid-character-in-node-name");
/* 399 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ReferenceToExternalEntity"), "wf-invalid-character");
/* 400 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ReferenceToUnparsedEntity"), "wf-invalid-character");
/*     */ 
/*     */       
/* 403 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingNotSupported"), "unsupported-encoding");
/* 404 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingRequired"), "unsupported-encoding");
/* 405 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName"), "wf-invalid-character-in-node-name");
/* 406 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementXMLNSPrefix"), "wf-invalid-character-in-node-name");
/* 407 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementPrefixUnbound"), "wf-invalid-character-in-node-name");
/* 408 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "AttributePrefixUnbound"), "wf-invalid-character-in-node-name");
/* 409 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "EmptyPrefixedAttName"), "wf-invalid-character-in-node-name");
/* 410 */       aDOMErrorTypeTable.put(new XMLErrorCode("http://www.w3.org/TR/1998/REC-xml-19980210", "PrefixDeclared"), "wf-invalid-character-in-node-name");
/*     */       
/* 412 */       fgDOMErrorTypeTable = Collections.unmodifiableMap(aDOMErrorTypeTable);
/*     */     }
/*     */     
/*     */     public static String getDOMErrorType(XMLErrorCode error) {
/* 416 */       return fgDOMErrorTypeTable.get(error);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\util\DOMErrorHandlerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */