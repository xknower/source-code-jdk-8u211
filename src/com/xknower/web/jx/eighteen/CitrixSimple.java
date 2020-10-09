package com.xknower.web.jx.eighteen;

/**
 * JxBrowser can be used in Citrix environment if it's configured in appropriate way.
 * The following instruction describes how Citrix environment should be configured to be able to run JxBrowser Demo application.
 * <p>
 * Using the approach described below you can configure Citrix to run any Java application based on JxBrowser library.
 * <p>
 * Preconditions
 * 1. Download and install VirtualBox.
 * 2. Download and install Windows Server 2012 on VirtualBox's VM (VM#1). Make sure that you use the Host Only network with no DHCP VM setting.
 * 3. Set static IP for installed VM#1.
 * 4. Add following server roles:
 * // 4.1. Active Directory Domain Services
 * // 4.2. DNS Server
 * 5. Configure Active Directory and DNS following to wizard’s instructions. Setup SSL according to the instruction.
 * 6. Install second VM with downloaded Windows Server 2012 (VM#2).
 * Make sure that you use the Host Only network on the same controller as VM#1 VM setting.
 * 7. Set static IP for installed VM#2 in the same sub-network as VM#1.
 * 8. Join VM#2 to Active Directory located on VM#1.
 * 9. Import SSL certificate from VM#1.
 * <p>
 * XenDesktop Setup
 * 1. Download Citrix XenDesktop.
 * 2. Install XenDesktop on VM#2. Note: it won’t install on domain controller.
 * 3. Install Delivery Controller.
 * 4. Configure the XenDesktop.
 * // 4.1. Add VM#2 into new Delivery Group and manually add Application as shown below:
 * // 4.2. Important: Do not configure StoreWeb yet!
 * 5. Install and configure Virtual Delivery Agent for Windows Server OS. After setup: change StoreWeb base URL to use HTTPS.
 * <p>
 * Test Configuration
 * 1. Install Citrix Receiver on VM#1. Install CitrixReceiver.exe. Connect to VM#2 using IE and allow installation of CitrixReceiver.exe.
 * 2. Ensure application could be started in Browser and close browser.
 * 3. Open Citrix Receiver, enter server name as vm1.domain.local and ensure that application could be started in Citrix Receiver client.
 */
public class CitrixSimple {
}
