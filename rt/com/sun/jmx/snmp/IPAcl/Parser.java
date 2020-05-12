/*      */ package com.sun.jmx.snmp.IPAcl;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Parser
/*      */   implements ParserTreeConstants, ParserConstants
/*      */ {
/*   33 */   protected JJTParserState jjtree = new JJTParserState(); public ParserTokenManager token_source; ASCII_CharStream jj_input_stream; public Token token; public Token jj_nt; private int jj_ntk;
/*      */   private Token jj_scanpos;
/*      */   private Token jj_lastpos;
/*      */   private int jj_la;
/*      */   
/*      */   public final JDMSecurityDefs SecurityDefs() throws ParseException {
/*   39 */     JDMSecurityDefs jDMSecurityDefs = new JDMSecurityDefs(0);
/*   40 */     boolean bool = true;
/*   41 */     this.jjtree.openNodeScope(jDMSecurityDefs);
/*      */     try {
/*   43 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 8:
/*   45 */           AclBlock();
/*      */           break;
/*      */         default:
/*   48 */           this.jj_la1[0] = this.jj_gen;
/*      */           break;
/*      */       } 
/*   51 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 19:
/*   53 */           TrapBlock();
/*      */           break;
/*      */         default:
/*   56 */           this.jj_la1[1] = this.jj_gen;
/*      */           break;
/*      */       } 
/*   59 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 20:
/*   61 */           InformBlock();
/*      */           break;
/*      */         default:
/*   64 */           this.jj_la1[2] = this.jj_gen;
/*      */           break;
/*      */       } 
/*   67 */       jj_consume_token(0);
/*   68 */       this.jjtree.closeNodeScope(jDMSecurityDefs, true);
/*   69 */       bool = false;
/*   70 */       return jDMSecurityDefs;
/*   71 */     } catch (Throwable throwable) {
/*   72 */       if (bool) {
/*   73 */         this.jjtree.clearNodeScope(jDMSecurityDefs);
/*   74 */         bool = false;
/*      */       } else {
/*   76 */         this.jjtree.popNode();
/*      */       } 
/*   78 */       if (throwable instanceof RuntimeException) {
/*   79 */         throw (RuntimeException)throwable;
/*      */       }
/*   81 */       if (throwable instanceof ParseException) {
/*   82 */         throw (ParseException)throwable;
/*      */       }
/*   84 */       throw (Error)throwable;
/*      */     } finally {
/*   86 */       if (bool) {
/*   87 */         this.jjtree.closeNodeScope(jDMSecurityDefs, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void AclBlock() throws ParseException {
/*   95 */     JDMAclBlock jDMAclBlock = new JDMAclBlock(1);
/*   96 */     boolean bool = true;
/*   97 */     this.jjtree.openNodeScope(jDMAclBlock);
/*      */     try {
/*   99 */       jj_consume_token(8);
/*  100 */       jj_consume_token(9);
/*  101 */       jj_consume_token(13);
/*      */       
/*      */       while (true) {
/*  104 */         AclItem();
/*  105 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 13:
/*      */             continue;
/*      */         }  break;
/*      */       } 
/*  110 */       this.jj_la1[3] = this.jj_gen;
/*      */ 
/*      */ 
/*      */       
/*  114 */       jj_consume_token(16);
/*  115 */     } catch (Throwable throwable) {
/*  116 */       if (bool) {
/*  117 */         this.jjtree.clearNodeScope(jDMAclBlock);
/*  118 */         bool = false;
/*      */       } else {
/*  120 */         this.jjtree.popNode();
/*      */       } 
/*  122 */       if (throwable instanceof RuntimeException) {
/*  123 */         throw (RuntimeException)throwable;
/*      */       }
/*  125 */       if (throwable instanceof ParseException) {
/*  126 */         throw (ParseException)throwable;
/*      */       }
/*  128 */       throw (Error)throwable;
/*      */     } finally {
/*  130 */       if (bool) {
/*  131 */         this.jjtree.closeNodeScope(jDMAclBlock, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void AclItem() throws ParseException {
/*  138 */     JDMAclItem jDMAclItem = new JDMAclItem(2);
/*  139 */     boolean bool = true;
/*  140 */     this.jjtree.openNodeScope(jDMAclItem);
/*      */     try {
/*  142 */       jj_consume_token(13);
/*  143 */       jDMAclItem.com = Communities();
/*  144 */       jDMAclItem.access = Access();
/*  145 */       Managers();
/*  146 */       jj_consume_token(16);
/*  147 */     } catch (Throwable throwable) {
/*  148 */       if (bool) {
/*  149 */         this.jjtree.clearNodeScope(jDMAclItem);
/*  150 */         bool = false;
/*      */       } else {
/*  152 */         this.jjtree.popNode();
/*      */       } 
/*  154 */       if (throwable instanceof RuntimeException) {
/*  155 */         throw (RuntimeException)throwable;
/*      */       }
/*  157 */       if (throwable instanceof ParseException) {
/*  158 */         throw (ParseException)throwable;
/*      */       }
/*  160 */       throw (Error)throwable;
/*      */     } finally {
/*  162 */       if (bool) {
/*  163 */         this.jjtree.closeNodeScope(jDMAclItem, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final JDMCommunities Communities() throws ParseException {
/*  170 */     JDMCommunities jDMCommunities = new JDMCommunities(3);
/*  171 */     boolean bool = true;
/*  172 */     this.jjtree.openNodeScope(jDMCommunities);
/*      */     try {
/*  174 */       jj_consume_token(10);
/*  175 */       jj_consume_token(9);
/*  176 */       Community();
/*      */       
/*      */       while (true) {
/*  179 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 36:
/*      */             break;
/*      */           
/*      */           default:
/*  184 */             this.jj_la1[4] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  187 */         jj_consume_token(36);
/*  188 */         Community();
/*      */       } 
/*  190 */       this.jjtree.closeNodeScope(jDMCommunities, true);
/*  191 */       bool = false;
/*  192 */       return jDMCommunities;
/*  193 */     } catch (Throwable throwable) {
/*  194 */       if (bool) {
/*  195 */         this.jjtree.clearNodeScope(jDMCommunities);
/*  196 */         bool = false;
/*      */       } else {
/*  198 */         this.jjtree.popNode();
/*      */       } 
/*  200 */       if (throwable instanceof RuntimeException) {
/*  201 */         throw (RuntimeException)throwable;
/*      */       }
/*  203 */       if (throwable instanceof ParseException) {
/*  204 */         throw (ParseException)throwable;
/*      */       }
/*  206 */       throw (Error)throwable;
/*      */     } finally {
/*  208 */       if (bool) {
/*  209 */         this.jjtree.closeNodeScope(jDMCommunities, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void Community() throws ParseException {
/*  217 */     JDMCommunity jDMCommunity = new JDMCommunity(4);
/*  218 */     boolean bool = true;
/*  219 */     this.jjtree.openNodeScope(jDMCommunity);
/*      */     try {
/*  221 */       Token token = jj_consume_token(31);
/*  222 */       this.jjtree.closeNodeScope(jDMCommunity, true);
/*  223 */       bool = false;
/*  224 */       jDMCommunity.communityString = token.image;
/*      */     } finally {
/*  226 */       if (bool) {
/*  227 */         this.jjtree.closeNodeScope(jDMCommunity, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final JDMAccess Access() throws ParseException {
/*  234 */     JDMAccess jDMAccess = new JDMAccess(5);
/*  235 */     boolean bool = true;
/*  236 */     this.jjtree.openNodeScope(jDMAccess);
/*      */     try {
/*  238 */       jj_consume_token(7);
/*  239 */       jj_consume_token(9);
/*  240 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 17:
/*  242 */           jj_consume_token(17);
/*  243 */           jDMAccess.access = 17;
/*      */           break;
/*      */         case 18:
/*  246 */           jj_consume_token(18);
/*  247 */           jDMAccess.access = 18;
/*      */           break;
/*      */         default:
/*  250 */           this.jj_la1[5] = this.jj_gen;
/*  251 */           jj_consume_token(-1);
/*  252 */           throw new ParseException();
/*      */       } 
/*  254 */       this.jjtree.closeNodeScope(jDMAccess, true);
/*  255 */       bool = false;
/*  256 */       return jDMAccess;
/*      */     } finally {
/*  258 */       if (bool) {
/*  259 */         this.jjtree.closeNodeScope(jDMAccess, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void Managers() throws ParseException {
/*  267 */     JDMManagers jDMManagers = new JDMManagers(6);
/*  268 */     boolean bool = true;
/*  269 */     this.jjtree.openNodeScope(jDMManagers);
/*      */     try {
/*  271 */       jj_consume_token(14);
/*  272 */       jj_consume_token(9);
/*  273 */       Host();
/*      */       
/*      */       while (true) {
/*  276 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 36:
/*      */             break;
/*      */           
/*      */           default:
/*  281 */             this.jj_la1[6] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  284 */         jj_consume_token(36);
/*  285 */         Host();
/*      */       } 
/*  287 */     } catch (Throwable throwable) {
/*  288 */       if (bool) {
/*  289 */         this.jjtree.clearNodeScope(jDMManagers);
/*  290 */         bool = false;
/*      */       } else {
/*  292 */         this.jjtree.popNode();
/*      */       } 
/*  294 */       if (throwable instanceof RuntimeException) {
/*  295 */         throw (RuntimeException)throwable;
/*      */       }
/*  297 */       if (throwable instanceof ParseException) {
/*  298 */         throw (ParseException)throwable;
/*      */       }
/*  300 */       throw (Error)throwable;
/*      */     } finally {
/*  302 */       if (bool) {
/*  303 */         this.jjtree.closeNodeScope(jDMManagers, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void Host() throws ParseException {
/*  310 */     JDMHost jDMHost = new JDMHost(7);
/*  311 */     boolean bool = true;
/*  312 */     this.jjtree.openNodeScope(jDMHost);
/*      */     try {
/*  314 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 31:
/*  316 */           HostName();
/*      */           break;
/*      */         default:
/*  319 */           this.jj_la1[7] = this.jj_gen;
/*  320 */           if (jj_2_1(2147483647)) {
/*  321 */             NetMask(); break;
/*  322 */           }  if (jj_2_2(2147483647)) {
/*  323 */             NetMaskV6(); break;
/*  324 */           }  if (jj_2_3(2147483647)) {
/*  325 */             IpAddress(); break;
/*      */           } 
/*  327 */           switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */             case 28:
/*  329 */               IpV6Address();
/*      */               break;
/*      */             case 24:
/*  332 */               IpMask();
/*      */               break;
/*      */           } 
/*  335 */           this.jj_la1[8] = this.jj_gen;
/*  336 */           jj_consume_token(-1);
/*  337 */           throw new ParseException();
/*      */       } 
/*      */ 
/*      */     
/*  341 */     } catch (Throwable throwable) {
/*  342 */       if (bool) {
/*  343 */         this.jjtree.clearNodeScope(jDMHost);
/*  344 */         bool = false;
/*      */       } else {
/*  346 */         this.jjtree.popNode();
/*      */       } 
/*  348 */       if (throwable instanceof RuntimeException) {
/*  349 */         throw (RuntimeException)throwable;
/*      */       }
/*  351 */       if (throwable instanceof ParseException) {
/*  352 */         throw (ParseException)throwable;
/*      */       }
/*  354 */       throw (Error)throwable;
/*      */     } finally {
/*  356 */       if (bool) {
/*  357 */         this.jjtree.closeNodeScope(jDMHost, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void HostName() throws ParseException {
/*  364 */     JDMHostName jDMHostName = new JDMHostName(8);
/*  365 */     boolean bool = true;
/*  366 */     this.jjtree.openNodeScope(jDMHostName);
/*      */     try {
/*  368 */       Token token = jj_consume_token(31);
/*  369 */       jDMHostName.name.append(token.image);
/*      */       
/*      */       while (true) {
/*  372 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 37:
/*      */             break;
/*      */           
/*      */           default:
/*  377 */             this.jj_la1[9] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  380 */         jj_consume_token(37);
/*  381 */         token = jj_consume_token(31);
/*  382 */         jDMHostName.name.append("." + token.image);
/*      */       } 
/*      */     } finally {
/*  385 */       if (bool) {
/*  386 */         this.jjtree.closeNodeScope(jDMHostName, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void IpAddress() throws ParseException {
/*  393 */     JDMIpAddress jDMIpAddress = new JDMIpAddress(9);
/*  394 */     boolean bool = true;
/*  395 */     this.jjtree.openNodeScope(jDMIpAddress);
/*      */     try {
/*  397 */       Token token = jj_consume_token(24);
/*  398 */       jDMIpAddress.address.append(token.image);
/*      */       
/*      */       while (true) {
/*  401 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 37:
/*      */             break;
/*      */           
/*      */           default:
/*  406 */             this.jj_la1[10] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  409 */         jj_consume_token(37);
/*  410 */         token = jj_consume_token(24);
/*  411 */         jDMIpAddress.address.append("." + token.image);
/*      */       } 
/*      */     } finally {
/*  414 */       if (bool) {
/*  415 */         this.jjtree.closeNodeScope(jDMIpAddress, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void IpV6Address() throws ParseException {
/*  422 */     JDMIpV6Address jDMIpV6Address = new JDMIpV6Address(10);
/*  423 */     boolean bool = true;
/*  424 */     this.jjtree.openNodeScope(jDMIpV6Address);
/*      */     try {
/*  426 */       Token token = jj_consume_token(28);
/*  427 */       this.jjtree.closeNodeScope(jDMIpV6Address, true);
/*  428 */       bool = false;
/*  429 */       jDMIpV6Address.address.append(token.image);
/*      */     } finally {
/*  431 */       if (bool) {
/*  432 */         this.jjtree.closeNodeScope(jDMIpV6Address, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void IpMask() throws ParseException {
/*  439 */     JDMIpMask jDMIpMask = new JDMIpMask(11);
/*  440 */     boolean bool = true;
/*  441 */     this.jjtree.openNodeScope(jDMIpMask);
/*      */     try {
/*  443 */       Token token = jj_consume_token(24);
/*  444 */       jDMIpMask.address.append(token.image);
/*      */       
/*      */       while (true) {
/*  447 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 38:
/*      */             break;
/*      */           
/*      */           default:
/*  452 */             this.jj_la1[11] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  455 */         jj_consume_token(38);
/*  456 */         token = jj_consume_token(24);
/*  457 */         jDMIpMask.address.append("." + token.image);
/*      */       } 
/*      */     } finally {
/*  460 */       if (bool) {
/*  461 */         this.jjtree.closeNodeScope(jDMIpMask, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void NetMask() throws ParseException {
/*  468 */     JDMNetMask jDMNetMask = new JDMNetMask(12);
/*  469 */     boolean bool = true;
/*  470 */     this.jjtree.openNodeScope(jDMNetMask);
/*      */     try {
/*  472 */       Token token = jj_consume_token(24);
/*  473 */       jDMNetMask.address.append(token.image);
/*      */       
/*      */       while (true) {
/*  476 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 37:
/*      */             break;
/*      */           
/*      */           default:
/*  481 */             this.jj_la1[12] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  484 */         jj_consume_token(37);
/*  485 */         token = jj_consume_token(24);
/*  486 */         jDMNetMask.address.append("." + token.image);
/*      */       } 
/*  488 */       jj_consume_token(39);
/*  489 */       token = jj_consume_token(24);
/*  490 */       this.jjtree.closeNodeScope(jDMNetMask, true);
/*  491 */       bool = false;
/*  492 */       jDMNetMask.mask = token.image;
/*      */     } finally {
/*  494 */       if (bool) {
/*  495 */         this.jjtree.closeNodeScope(jDMNetMask, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void NetMaskV6() throws ParseException {
/*  502 */     JDMNetMaskV6 jDMNetMaskV6 = new JDMNetMaskV6(13);
/*  503 */     boolean bool = true;
/*  504 */     this.jjtree.openNodeScope(jDMNetMaskV6);
/*      */     try {
/*  506 */       Token token = jj_consume_token(28);
/*  507 */       jDMNetMaskV6.address.append(token.image);
/*  508 */       jj_consume_token(39);
/*  509 */       token = jj_consume_token(24);
/*  510 */       this.jjtree.closeNodeScope(jDMNetMaskV6, true);
/*  511 */       bool = false;
/*  512 */       jDMNetMaskV6.mask = token.image;
/*      */     } finally {
/*  514 */       if (bool) {
/*  515 */         this.jjtree.closeNodeScope(jDMNetMaskV6, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void TrapBlock() throws ParseException {
/*  522 */     JDMTrapBlock jDMTrapBlock = new JDMTrapBlock(14);
/*  523 */     boolean bool = true;
/*  524 */     this.jjtree.openNodeScope(jDMTrapBlock);
/*      */     try {
/*  526 */       jj_consume_token(19);
/*  527 */       jj_consume_token(9);
/*  528 */       jj_consume_token(13);
/*      */       
/*      */       while (true) {
/*  531 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 13:
/*      */             break;
/*      */           
/*      */           default:
/*  536 */             this.jj_la1[13] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  539 */         TrapItem();
/*      */       } 
/*  541 */       jj_consume_token(16);
/*  542 */     } catch (Throwable throwable) {
/*  543 */       if (bool) {
/*  544 */         this.jjtree.clearNodeScope(jDMTrapBlock);
/*  545 */         bool = false;
/*      */       } else {
/*  547 */         this.jjtree.popNode();
/*      */       } 
/*  549 */       if (throwable instanceof RuntimeException) {
/*  550 */         throw (RuntimeException)throwable;
/*      */       }
/*  552 */       if (throwable instanceof ParseException) {
/*  553 */         throw (ParseException)throwable;
/*      */       }
/*  555 */       throw (Error)throwable;
/*      */     } finally {
/*  557 */       if (bool) {
/*  558 */         this.jjtree.closeNodeScope(jDMTrapBlock, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void TrapItem() throws ParseException {
/*  565 */     JDMTrapItem jDMTrapItem = new JDMTrapItem(15);
/*  566 */     boolean bool = true;
/*  567 */     this.jjtree.openNodeScope(jDMTrapItem);
/*      */     try {
/*  569 */       jj_consume_token(13);
/*  570 */       jDMTrapItem.comm = TrapCommunity();
/*  571 */       TrapInterestedHost();
/*      */       
/*      */       while (true) {
/*  574 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 13:
/*      */             break;
/*      */           
/*      */           default:
/*  579 */             this.jj_la1[14] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  582 */         Enterprise();
/*      */       } 
/*  584 */       jj_consume_token(16);
/*  585 */     } catch (Throwable throwable) {
/*  586 */       if (bool) {
/*  587 */         this.jjtree.clearNodeScope(jDMTrapItem);
/*  588 */         bool = false;
/*      */       } else {
/*  590 */         this.jjtree.popNode();
/*      */       } 
/*  592 */       if (throwable instanceof RuntimeException) {
/*  593 */         throw (RuntimeException)throwable;
/*      */       }
/*  595 */       if (throwable instanceof ParseException) {
/*  596 */         throw (ParseException)throwable;
/*      */       }
/*  598 */       throw (Error)throwable;
/*      */     } finally {
/*  600 */       if (bool) {
/*  601 */         this.jjtree.closeNodeScope(jDMTrapItem, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final JDMTrapCommunity TrapCommunity() throws ParseException {
/*  608 */     JDMTrapCommunity jDMTrapCommunity = new JDMTrapCommunity(16);
/*  609 */     boolean bool = true;
/*  610 */     this.jjtree.openNodeScope(jDMTrapCommunity);
/*      */     try {
/*  612 */       jj_consume_token(21);
/*  613 */       jj_consume_token(9);
/*  614 */       Token token = jj_consume_token(31);
/*  615 */       this.jjtree.closeNodeScope(jDMTrapCommunity, true);
/*  616 */       bool = false;
/*  617 */       jDMTrapCommunity.community = token.image; return jDMTrapCommunity;
/*      */     } finally {
/*  619 */       if (bool) {
/*  620 */         this.jjtree.closeNodeScope(jDMTrapCommunity, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void TrapInterestedHost() throws ParseException {
/*  628 */     JDMTrapInterestedHost jDMTrapInterestedHost = new JDMTrapInterestedHost(17);
/*  629 */     boolean bool = true;
/*  630 */     this.jjtree.openNodeScope(jDMTrapInterestedHost);
/*      */     try {
/*  632 */       jj_consume_token(12);
/*  633 */       jj_consume_token(9);
/*  634 */       HostTrap();
/*      */       
/*      */       while (true) {
/*  637 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 36:
/*      */             break;
/*      */           
/*      */           default:
/*  642 */             this.jj_la1[15] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  645 */         jj_consume_token(36);
/*  646 */         HostTrap();
/*      */       } 
/*  648 */     } catch (Throwable throwable) {
/*  649 */       if (bool) {
/*  650 */         this.jjtree.clearNodeScope(jDMTrapInterestedHost);
/*  651 */         bool = false;
/*      */       } else {
/*  653 */         this.jjtree.popNode();
/*      */       } 
/*  655 */       if (throwable instanceof RuntimeException) {
/*  656 */         throw (RuntimeException)throwable;
/*      */       }
/*  658 */       if (throwable instanceof ParseException) {
/*  659 */         throw (ParseException)throwable;
/*      */       }
/*  661 */       throw (Error)throwable;
/*      */     } finally {
/*  663 */       if (bool) {
/*  664 */         this.jjtree.closeNodeScope(jDMTrapInterestedHost, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void HostTrap() throws ParseException {
/*  671 */     JDMHostTrap jDMHostTrap = new JDMHostTrap(18);
/*  672 */     boolean bool = true;
/*  673 */     this.jjtree.openNodeScope(jDMHostTrap);
/*      */     try {
/*  675 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 31:
/*  677 */           HostName();
/*      */           break;
/*      */         case 24:
/*  680 */           IpAddress();
/*      */           break;
/*      */         case 28:
/*  683 */           IpV6Address();
/*      */           break;
/*      */         default:
/*  686 */           this.jj_la1[16] = this.jj_gen;
/*  687 */           jj_consume_token(-1);
/*  688 */           throw new ParseException();
/*      */       } 
/*  690 */     } catch (Throwable throwable) {
/*  691 */       if (bool) {
/*  692 */         this.jjtree.clearNodeScope(jDMHostTrap);
/*  693 */         bool = false;
/*      */       } else {
/*  695 */         this.jjtree.popNode();
/*      */       } 
/*  697 */       if (throwable instanceof RuntimeException) {
/*  698 */         throw (RuntimeException)throwable;
/*      */       }
/*  700 */       if (throwable instanceof ParseException) {
/*  701 */         throw (ParseException)throwable;
/*      */       }
/*  703 */       throw (Error)throwable;
/*      */     } finally {
/*  705 */       if (bool) {
/*  706 */         this.jjtree.closeNodeScope(jDMHostTrap, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void Enterprise() throws ParseException {
/*  713 */     JDMEnterprise jDMEnterprise = new JDMEnterprise(19);
/*  714 */     boolean bool = true;
/*  715 */     this.jjtree.openNodeScope(jDMEnterprise);
/*      */     try {
/*  717 */       jj_consume_token(13);
/*  718 */       jj_consume_token(11);
/*  719 */       jj_consume_token(9);
/*  720 */       Token token = jj_consume_token(35);
/*  721 */       jDMEnterprise.enterprise = token.image;
/*  722 */       jj_consume_token(23);
/*  723 */       jj_consume_token(9);
/*  724 */       TrapNum();
/*      */       
/*      */       while (true) {
/*  727 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 36:
/*      */             break;
/*      */           
/*      */           default:
/*  732 */             this.jj_la1[17] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  735 */         jj_consume_token(36);
/*  736 */         TrapNum();
/*      */       } 
/*  738 */       jj_consume_token(16);
/*  739 */     } catch (Throwable throwable) {
/*  740 */       if (bool) {
/*  741 */         this.jjtree.clearNodeScope(jDMEnterprise);
/*  742 */         bool = false;
/*      */       } else {
/*  744 */         this.jjtree.popNode();
/*      */       } 
/*  746 */       if (throwable instanceof RuntimeException) {
/*  747 */         throw (RuntimeException)throwable;
/*      */       }
/*  749 */       if (throwable instanceof ParseException) {
/*  750 */         throw (ParseException)throwable;
/*      */       }
/*  752 */       throw (Error)throwable;
/*      */     } finally {
/*  754 */       if (bool) {
/*  755 */         this.jjtree.closeNodeScope(jDMEnterprise, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void TrapNum() throws ParseException {
/*  762 */     JDMTrapNum jDMTrapNum = new JDMTrapNum(20);
/*  763 */     boolean bool = true;
/*  764 */     this.jjtree.openNodeScope(jDMTrapNum);
/*      */     try {
/*  766 */       Token token = jj_consume_token(24);
/*  767 */       jDMTrapNum.low = Integer.parseInt(token.image);
/*  768 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 15:
/*  770 */           jj_consume_token(15);
/*  771 */           token = jj_consume_token(24);
/*  772 */           jDMTrapNum.high = Integer.parseInt(token.image);
/*      */           break;
/*      */         default:
/*  775 */           this.jj_la1[18] = this.jj_gen;
/*      */           break;
/*      */       } 
/*      */     } finally {
/*  779 */       if (bool) {
/*  780 */         this.jjtree.closeNodeScope(jDMTrapNum, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void InformBlock() throws ParseException {
/*  787 */     JDMInformBlock jDMInformBlock = new JDMInformBlock(21);
/*  788 */     boolean bool = true;
/*  789 */     this.jjtree.openNodeScope(jDMInformBlock);
/*      */     try {
/*  791 */       jj_consume_token(20);
/*  792 */       jj_consume_token(9);
/*  793 */       jj_consume_token(13);
/*      */       
/*      */       while (true) {
/*  796 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 13:
/*      */             break;
/*      */           
/*      */           default:
/*  801 */             this.jj_la1[19] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  804 */         InformItem();
/*      */       } 
/*  806 */       jj_consume_token(16);
/*  807 */     } catch (Throwable throwable) {
/*  808 */       if (bool) {
/*  809 */         this.jjtree.clearNodeScope(jDMInformBlock);
/*  810 */         bool = false;
/*      */       } else {
/*  812 */         this.jjtree.popNode();
/*      */       } 
/*  814 */       if (throwable instanceof RuntimeException) {
/*  815 */         throw (RuntimeException)throwable;
/*      */       }
/*  817 */       if (throwable instanceof ParseException) {
/*  818 */         throw (ParseException)throwable;
/*      */       }
/*  820 */       throw (Error)throwable;
/*      */     } finally {
/*  822 */       if (bool) {
/*  823 */         this.jjtree.closeNodeScope(jDMInformBlock, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void InformItem() throws ParseException {
/*  830 */     JDMInformItem jDMInformItem = new JDMInformItem(22);
/*  831 */     boolean bool = true;
/*  832 */     this.jjtree.openNodeScope(jDMInformItem);
/*      */     try {
/*  834 */       jj_consume_token(13);
/*  835 */       jDMInformItem.comm = InformCommunity();
/*  836 */       InformInterestedHost();
/*  837 */       jj_consume_token(16);
/*  838 */     } catch (Throwable throwable) {
/*  839 */       if (bool) {
/*  840 */         this.jjtree.clearNodeScope(jDMInformItem);
/*  841 */         bool = false;
/*      */       } else {
/*  843 */         this.jjtree.popNode();
/*      */       } 
/*  845 */       if (throwable instanceof RuntimeException) {
/*  846 */         throw (RuntimeException)throwable;
/*      */       }
/*  848 */       if (throwable instanceof ParseException) {
/*  849 */         throw (ParseException)throwable;
/*      */       }
/*  851 */       throw (Error)throwable;
/*      */     } finally {
/*  853 */       if (bool) {
/*  854 */         this.jjtree.closeNodeScope(jDMInformItem, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final JDMInformCommunity InformCommunity() throws ParseException {
/*  861 */     JDMInformCommunity jDMInformCommunity = new JDMInformCommunity(23);
/*  862 */     boolean bool = true;
/*  863 */     this.jjtree.openNodeScope(jDMInformCommunity);
/*      */     try {
/*  865 */       jj_consume_token(22);
/*  866 */       jj_consume_token(9);
/*  867 */       Token token = jj_consume_token(31);
/*  868 */       this.jjtree.closeNodeScope(jDMInformCommunity, true);
/*  869 */       bool = false;
/*  870 */       jDMInformCommunity.community = token.image; return jDMInformCommunity;
/*      */     } finally {
/*  872 */       if (bool) {
/*  873 */         this.jjtree.closeNodeScope(jDMInformCommunity, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void InformInterestedHost() throws ParseException {
/*  881 */     JDMInformInterestedHost jDMInformInterestedHost = new JDMInformInterestedHost(24);
/*  882 */     boolean bool = true;
/*  883 */     this.jjtree.openNodeScope(jDMInformInterestedHost);
/*      */     try {
/*  885 */       jj_consume_token(12);
/*  886 */       jj_consume_token(9);
/*  887 */       HostInform();
/*      */       
/*      */       while (true) {
/*  890 */         switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */           case 36:
/*      */             break;
/*      */           
/*      */           default:
/*  895 */             this.jj_la1[20] = this.jj_gen;
/*      */             break;
/*      */         } 
/*  898 */         jj_consume_token(36);
/*  899 */         HostInform();
/*      */       } 
/*  901 */     } catch (Throwable throwable) {
/*  902 */       if (bool) {
/*  903 */         this.jjtree.clearNodeScope(jDMInformInterestedHost);
/*  904 */         bool = false;
/*      */       } else {
/*  906 */         this.jjtree.popNode();
/*      */       } 
/*  908 */       if (throwable instanceof RuntimeException) {
/*  909 */         throw (RuntimeException)throwable;
/*      */       }
/*  911 */       if (throwable instanceof ParseException) {
/*  912 */         throw (ParseException)throwable;
/*      */       }
/*  914 */       throw (Error)throwable;
/*      */     } finally {
/*  916 */       if (bool) {
/*  917 */         this.jjtree.closeNodeScope(jDMInformInterestedHost, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void HostInform() throws ParseException {
/*  924 */     JDMHostInform jDMHostInform = new JDMHostInform(25);
/*  925 */     boolean bool = true;
/*  926 */     this.jjtree.openNodeScope(jDMHostInform);
/*      */     try {
/*  928 */       switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
/*      */         case 31:
/*  930 */           HostName();
/*      */           break;
/*      */         case 24:
/*  933 */           IpAddress();
/*      */           break;
/*      */         case 28:
/*  936 */           IpV6Address();
/*      */           break;
/*      */         default:
/*  939 */           this.jj_la1[21] = this.jj_gen;
/*  940 */           jj_consume_token(-1);
/*  941 */           throw new ParseException();
/*      */       } 
/*  943 */     } catch (Throwable throwable) {
/*  944 */       if (bool) {
/*  945 */         this.jjtree.clearNodeScope(jDMHostInform);
/*  946 */         bool = false;
/*      */       } else {
/*  948 */         this.jjtree.popNode();
/*      */       } 
/*  950 */       if (throwable instanceof RuntimeException) {
/*  951 */         throw (RuntimeException)throwable;
/*      */       }
/*  953 */       if (throwable instanceof ParseException) {
/*  954 */         throw (ParseException)throwable;
/*      */       }
/*  956 */       throw (Error)throwable;
/*      */     } finally {
/*  958 */       if (bool) {
/*  959 */         this.jjtree.closeNodeScope(jDMHostInform, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private final boolean jj_2_1(int paramInt) {
/*  965 */     this.jj_la = paramInt; this.jj_lastpos = this.jj_scanpos = this.token;
/*  966 */     boolean bool = !jj_3_1() ? true : false;
/*  967 */     jj_save(0, paramInt);
/*  968 */     return bool;
/*      */   }
/*      */   
/*      */   private final boolean jj_2_2(int paramInt) {
/*  972 */     this.jj_la = paramInt; this.jj_lastpos = this.jj_scanpos = this.token;
/*  973 */     boolean bool = !jj_3_2() ? true : false;
/*  974 */     jj_save(1, paramInt);
/*  975 */     return bool;
/*      */   }
/*      */   
/*      */   private final boolean jj_2_3(int paramInt) {
/*  979 */     this.jj_la = paramInt; this.jj_lastpos = this.jj_scanpos = this.token;
/*  980 */     boolean bool = !jj_3_3() ? true : false;
/*  981 */     jj_save(2, paramInt);
/*  982 */     return bool;
/*      */   }
/*      */   
/*      */   private final boolean jj_3_3() {
/*  986 */     if (jj_scan_token(24)) return true; 
/*  987 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/*  988 */     if (jj_scan_token(37)) return true; 
/*  989 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/*  990 */     return false;
/*      */   }
/*      */   
/*      */   private final boolean jj_3_2() {
/*  994 */     if (jj_scan_token(28)) return true; 
/*  995 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/*  996 */     if (jj_scan_token(39)) return true; 
/*  997 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/*  998 */     if (jj_scan_token(24)) return true; 
/*  999 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/* 1000 */     return false;
/*      */   }
/*      */   
/*      */   private final boolean jj_3_1() {
/* 1004 */     if (jj_scan_token(24)) return true; 
/* 1005 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false;
/*      */     
/*      */     while (true) {
/* 1008 */       Token token = this.jj_scanpos;
/* 1009 */       if (jj_3R_14()) { this.jj_scanpos = token; break; }
/* 1010 */        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/*      */     } 
/* 1012 */     if (jj_scan_token(39)) return true; 
/* 1013 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/* 1014 */     if (jj_scan_token(24)) return true; 
/* 1015 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/* 1016 */     return false;
/*      */   }
/*      */   
/*      */   private final boolean jj_3R_14() {
/* 1020 */     if (jj_scan_token(37)) return true; 
/* 1021 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/* 1022 */     if (jj_scan_token(24)) return true; 
/* 1023 */     if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) return false; 
/* 1024 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean lookingAhead = false;
/*      */ 
/*      */   
/*      */   private boolean jj_semLA;
/*      */   
/*      */   private int jj_gen;
/*      */   
/* 1036 */   private final int[] jj_la1 = new int[22];
/* 1037 */   private final int[] jj_la1_0 = new int[] { 256, 524288, 1048576, 8192, 0, 393216, 0, Integer.MIN_VALUE, 285212672, 0, 0, 0, 0, 8192, 8192, 0, -1862270976, 0, 32768, 8192, 0, -1862270976 };
/* 1038 */   private final int[] jj_la1_1 = new int[] { 0, 0, 0, 0, 16, 0, 16, 0, 0, 32, 32, 64, 32, 0, 0, 16, 0, 16, 0, 0, 16, 0 };
/* 1039 */   private final JJCalls[] jj_2_rtns = new JJCalls[3];
/*      */   private boolean jj_rescan = false;
/* 1041 */   private int jj_gc = 0;
/*      */   
/*      */   private Vector<int[]> jj_expentries;
/*      */   
/*      */   private int[] jj_expentry;
/*      */   
/*      */   private int jj_kind;
/*      */   
/*      */   private int[] jj_lasttokens;
/*      */   
/*      */   private int jj_endpos;
/*      */   
/*      */   public void ReInit(InputStream paramInputStream) {
/* 1054 */     this.jj_input_stream.ReInit(paramInputStream, 1, 1);
/* 1055 */     this.token_source.ReInit(this.jj_input_stream);
/* 1056 */     this.token = new Token();
/* 1057 */     this.jj_ntk = -1;
/* 1058 */     this.jjtree.reset();
/* 1059 */     this.jj_gen = 0; byte b;
/* 1060 */     for (b = 0; b < 22; ) { this.jj_la1[b] = -1; b++; }
/* 1061 */      for (b = 0; b < this.jj_2_rtns.length; ) { this.jj_2_rtns[b] = new JJCalls(); b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ReInit(Reader paramReader) {
/* 1075 */     this.jj_input_stream.ReInit(paramReader, 1, 1);
/* 1076 */     this.token_source.ReInit(this.jj_input_stream);
/* 1077 */     this.token = new Token();
/* 1078 */     this.jj_ntk = -1;
/* 1079 */     this.jjtree.reset();
/* 1080 */     this.jj_gen = 0; byte b;
/* 1081 */     for (b = 0; b < 22; ) { this.jj_la1[b] = -1; b++; }
/* 1082 */      for (b = 0; b < this.jj_2_rtns.length; ) { this.jj_2_rtns[b] = new JJCalls(); b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ReInit(ParserTokenManager paramParserTokenManager) {
/* 1095 */     this.token_source = paramParserTokenManager;
/* 1096 */     this.token = new Token();
/* 1097 */     this.jj_ntk = -1;
/* 1098 */     this.jjtree.reset();
/* 1099 */     this.jj_gen = 0; byte b;
/* 1100 */     for (b = 0; b < 22; ) { this.jj_la1[b] = -1; b++; }
/* 1101 */      for (b = 0; b < this.jj_2_rtns.length; ) { this.jj_2_rtns[b] = new JJCalls(); b++; }
/*      */   
/*      */   }
/*      */   private final Token jj_consume_token(int paramInt) throws ParseException {
/*      */     Token token;
/* 1106 */     if ((token = this.token).next != null) { this.token = this.token.next; }
/* 1107 */     else { this.token = this.token.next = this.token_source.getNextToken(); }
/* 1108 */      this.jj_ntk = -1;
/* 1109 */     if (this.token.kind == paramInt) {
/* 1110 */       this.jj_gen++;
/* 1111 */       if (++this.jj_gc > 100) {
/* 1112 */         this.jj_gc = 0;
/* 1113 */         for (byte b = 0; b < this.jj_2_rtns.length; b++) {
/* 1114 */           JJCalls jJCalls = this.jj_2_rtns[b];
/* 1115 */           while (jJCalls != null) {
/* 1116 */             if (jJCalls.gen < this.jj_gen) jJCalls.first = null; 
/* 1117 */             jJCalls = jJCalls.next;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1121 */       return this.token;
/*      */     } 
/* 1123 */     this.token = token;
/* 1124 */     this.jj_kind = paramInt;
/* 1125 */     throw generateParseException();
/*      */   }
/*      */   
/*      */   private final boolean jj_scan_token(int paramInt) {
/* 1129 */     if (this.jj_scanpos == this.jj_lastpos) {
/* 1130 */       this.jj_la--;
/* 1131 */       if (this.jj_scanpos.next == null) {
/* 1132 */         this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
/*      */       } else {
/* 1134 */         this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
/*      */       } 
/*      */     } else {
/* 1137 */       this.jj_scanpos = this.jj_scanpos.next;
/*      */     } 
/* 1139 */     if (this.jj_rescan) {
/* 1140 */       byte b = 0; Token token = this.token;
/* 1141 */       while (token != null && token != this.jj_scanpos) { b++; token = token.next; }
/* 1142 */        if (token != null) jj_add_error_token(paramInt, b); 
/*      */     } 
/* 1144 */     return (this.jj_scanpos.kind != paramInt);
/*      */   }
/*      */   
/*      */   public final Token getNextToken() {
/* 1148 */     if (this.token.next != null) { this.token = this.token.next; }
/* 1149 */     else { this.token = this.token.next = this.token_source.getNextToken(); }
/* 1150 */      this.jj_ntk = -1;
/* 1151 */     this.jj_gen++;
/* 1152 */     return this.token;
/*      */   }
/*      */   
/*      */   public final Token getToken(int paramInt) {
/* 1156 */     Token token = this.lookingAhead ? this.jj_scanpos : this.token;
/* 1157 */     for (byte b = 0; b < paramInt; b++) {
/* 1158 */       if (token.next != null) { token = token.next; }
/* 1159 */       else { token = token.next = this.token_source.getNextToken(); }
/*      */     
/* 1161 */     }  return token;
/*      */   }
/*      */   
/*      */   private final int jj_ntk() {
/* 1165 */     if ((this.jj_nt = this.token.next) == null) {
/* 1166 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/*      */     }
/* 1168 */     return this.jj_ntk = this.jj_nt.kind;
/*      */   }
/*      */   
/* 1171 */   public Parser(InputStream paramInputStream) { this.jj_expentries = (Vector)new Vector<>();
/*      */     
/* 1173 */     this.jj_kind = -1;
/* 1174 */     this.jj_lasttokens = new int[100]; this.jj_input_stream = new ASCII_CharStream(paramInputStream, 1, 1); this.token_source = new ParserTokenManager(this.jj_input_stream); this.token = new Token(); this.jj_ntk = -1; this.jj_gen = 0; byte b; for (b = 0; b < 22; ) { this.jj_la1[b] = -1; b++; }  for (b = 0; b < this.jj_2_rtns.length; ) { this.jj_2_rtns[b] = new JJCalls(); b++; }  } public Parser(Reader paramReader) { this.jj_expentries = (Vector)new Vector<>(); this.jj_kind = -1; this.jj_lasttokens = new int[100]; this.jj_input_stream = new ASCII_CharStream(paramReader, 1, 1); this.token_source = new ParserTokenManager(this.jj_input_stream); this.token = new Token(); this.jj_ntk = -1; this.jj_gen = 0; byte b; for (b = 0; b < 22; ) { this.jj_la1[b] = -1; b++; }  for (b = 0; b < this.jj_2_rtns.length; ) { this.jj_2_rtns[b] = new JJCalls(); b++; }  } public Parser(ParserTokenManager paramParserTokenManager) { this.jj_expentries = (Vector)new Vector<>(); this.jj_kind = -1; this.jj_lasttokens = new int[100]; this.token_source = paramParserTokenManager; this.token = new Token(); this.jj_ntk = -1; this.jj_gen = 0; byte b; for (b = 0; b < 22; ) {
/*      */       this.jj_la1[b] = -1; b++;
/*      */     }  for (b = 0; b < this.jj_2_rtns.length; ) {
/*      */       this.jj_2_rtns[b] = new JJCalls(); b++;
/* 1178 */     }  } private void jj_add_error_token(int paramInt1, int paramInt2) { if (paramInt2 >= 100)
/* 1179 */       return;  if (paramInt2 == this.jj_endpos + 1) {
/* 1180 */       this.jj_lasttokens[this.jj_endpos++] = paramInt1;
/* 1181 */     } else if (this.jj_endpos != 0) {
/* 1182 */       this.jj_expentry = new int[this.jj_endpos]; byte b;
/* 1183 */       for (b = 0; b < this.jj_endpos; b++) {
/* 1184 */         this.jj_expentry[b] = this.jj_lasttokens[b];
/*      */       }
/* 1186 */       b = 0;
/* 1187 */       for (Enumeration<int> enumeration = this.jj_expentries.elements(); enumeration.hasMoreElements(); ) {
/* 1188 */         int[] arrayOfInt = (int[])enumeration.nextElement();
/* 1189 */         if (arrayOfInt.length == this.jj_expentry.length) {
/* 1190 */           b = 1;
/* 1191 */           for (byte b1 = 0; b1 < this.jj_expentry.length; b1++) {
/* 1192 */             if (arrayOfInt[b1] != this.jj_expentry[b1]) {
/* 1193 */               b = 0;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1197 */           if (b != 0)
/*      */             break; 
/*      */         } 
/* 1200 */       }  if (b == 0) this.jj_expentries.addElement(this.jj_expentry); 
/* 1201 */       if (paramInt2 != 0) this.jj_lasttokens[(this.jj_endpos = paramInt2) - 1] = paramInt1; 
/*      */     }  }
/*      */ 
/*      */   
/*      */   public final ParseException generateParseException() {
/* 1206 */     this.jj_expentries.removeAllElements();
/* 1207 */     boolean[] arrayOfBoolean = new boolean[40]; byte b1;
/* 1208 */     for (b1 = 0; b1 < 40; b1++) {
/* 1209 */       arrayOfBoolean[b1] = false;
/*      */     }
/* 1211 */     if (this.jj_kind >= 0) {
/* 1212 */       arrayOfBoolean[this.jj_kind] = true;
/* 1213 */       this.jj_kind = -1;
/*      */     } 
/* 1215 */     for (b1 = 0; b1 < 22; b1++) {
/* 1216 */       if (this.jj_la1[b1] == this.jj_gen) {
/* 1217 */         for (byte b = 0; b < 32; b++) {
/* 1218 */           if ((this.jj_la1_0[b1] & 1 << b) != 0) {
/* 1219 */             arrayOfBoolean[b] = true;
/*      */           }
/* 1221 */           if ((this.jj_la1_1[b1] & 1 << b) != 0) {
/* 1222 */             arrayOfBoolean[32 + b] = true;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/* 1227 */     for (b1 = 0; b1 < 40; b1++) {
/* 1228 */       if (arrayOfBoolean[b1]) {
/* 1229 */         this.jj_expentry = new int[1];
/* 1230 */         this.jj_expentry[0] = b1;
/* 1231 */         this.jj_expentries.addElement(this.jj_expentry);
/*      */       } 
/*      */     } 
/* 1234 */     this.jj_endpos = 0;
/* 1235 */     jj_rescan_token();
/* 1236 */     jj_add_error_token(0, 0);
/* 1237 */     int[][] arrayOfInt = new int[this.jj_expentries.size()][];
/* 1238 */     for (byte b2 = 0; b2 < this.jj_expentries.size(); b2++) {
/* 1239 */       arrayOfInt[b2] = this.jj_expentries.elementAt(b2);
/*      */     }
/* 1241 */     return new ParseException(this.token, arrayOfInt, tokenImage);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void enable_tracing() {}
/*      */ 
/*      */   
/*      */   public final void disable_tracing() {}
/*      */   
/*      */   private final void jj_rescan_token() {
/* 1251 */     this.jj_rescan = true;
/* 1252 */     for (byte b = 0; b < 3; ) {
/* 1253 */       JJCalls jJCalls = this.jj_2_rtns[b];
/*      */       while (true)
/* 1255 */       { if (jJCalls.gen > this.jj_gen) {
/* 1256 */           this.jj_la = jJCalls.arg; this.jj_lastpos = this.jj_scanpos = jJCalls.first;
/* 1257 */           switch (b) { case 0:
/* 1258 */               jj_3_1(); break;
/* 1259 */             case 1: jj_3_2(); break;
/* 1260 */             case 2: jj_3_3(); break; }
/*      */         
/*      */         } 
/* 1263 */         jJCalls = jJCalls.next;
/* 1264 */         if (jJCalls == null)
/*      */           b++;  } 
/* 1266 */     }  this.jj_rescan = false;
/*      */   }
/*      */   
/*      */   private final void jj_save(int paramInt1, int paramInt2) {
/* 1270 */     JJCalls jJCalls = this.jj_2_rtns[paramInt1];
/* 1271 */     while (jJCalls.gen > this.jj_gen) {
/* 1272 */       if (jJCalls.next == null) { jJCalls = jJCalls.next = new JJCalls(); break; }
/* 1273 */        jJCalls = jJCalls.next;
/*      */     } 
/* 1275 */     jJCalls.gen = this.jj_gen + paramInt2 - this.jj_la; jJCalls.first = this.token; jJCalls.arg = paramInt2;
/*      */   }
/*      */   
/*      */   static final class JJCalls {
/*      */     int gen;
/*      */     Token first;
/*      */     int arg;
/*      */     JJCalls next;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\Parser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */