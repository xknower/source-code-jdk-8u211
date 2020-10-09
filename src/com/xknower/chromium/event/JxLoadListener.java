package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.*;

import java.util.EventListener;
import java.util.List;

/**
 * [LoadListener -> BrowserListener -> EventListener]
 *
 * @author xknower
 */
public class JxLoadListener extends LoadAdapter implements LoadListener, BrowserListener, EventListener {

    /**
     * 添加事件处理, 可以获取加载的窗口编号和发生事件的浏览器, 判断是否时主窗口发生的事件
     * <p>
     * 启动加载
     *
     * @param startLoadingEvent [StartLoadingEvent -> LoadEvent -> BrowserEvent]
     */
    @Override
    public void onStartLoadingFrame(StartLoadingEvent startLoadingEvent) {
    }

    /**
     * @param provisionalLoadingEvent [ProvisionalLoadingEvent -> LoadEvent -> BrowserEvent]
     */
    @Override
    public void onProvisionalLoadingFrame(ProvisionalLoadingEvent provisionalLoadingEvent) {
    }

    /**
     * 加载 Frame 完成
     *
     * @param finishLoadingEvent [FinishLoadingEvent -> LoadEvent -> BrowserEvent]
     */
    @Override
    public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
    }

    /**
     * 加载 Frame 失败
     *
     * @param failLoadingEvent [FailLoadingEvent -> LoadEvent -> BrowserEvent]
     */
    @Override
    public void onFailLoadingFrame(FailLoadingEvent failLoadingEvent) {
    }

    /**
     * DOM 加载 Frame 完成
     *
     * @param frameLoadEvent [FrameLoadEvent -> BrowserEvent]
     */
    @Override
    public void onDocumentLoadedInFrame(FrameLoadEvent frameLoadEvent) {
    }

    /**
     * DOM 加载 MainFrame 完成 [LoadEvent -> BrowserEvent]
     *
     * @param loadEvent LoadEvent
     */
    @Override
    public void onDocumentLoadedInMainFrame(LoadEvent loadEvent) {
    }

    //

    private void generalProcessing(BrowserEvent event) {
        String browserInfo =
                String.format(
                        "浏览器[ %s | %s | %s]",
                        event.getBrowser().getTitle(),
                        event.getBrowser().getType().name(),
                        event.getBrowser().getFramesIds());
        System.out.println(browserInfo);
        if (event instanceof StartLoadingEvent) {
            StartLoadingEvent e = (StartLoadingEvent) event;
            String frameInfo =
                    String.format(
                            "StartLoadingEvent 窗口[ %s | Main is %s ] [%s]",
                            e.getFrameId(),
                            e.isMainFrame(), e.getValidatedURL());
            System.out.println(frameInfo);
        } else if (event instanceof ProvisionalLoadingEvent) {
            ProvisionalLoadingEvent e = (ProvisionalLoadingEvent) event;
            String frameInfo =
                    String.format(
                            "ProvisionalLoadingEvent 窗口[ %s | Main is %s ] [ %s ]",
                            e.getFrameId(),
                            e.isMainFrame(), e.getURL());
            System.out.println(frameInfo);
        } else if (event instanceof FinishLoadingEvent) {
            FinishLoadingEvent e = (FinishLoadingEvent) event;
            String frameInfo =
                    String.format(
                            "FinishLoadingEvent 窗口[ %s | Main is %s ] [ %s ]",
                            e.getFrameId(),
                            e.isMainFrame(), e.getValidatedURL());
            System.out.println(frameInfo);
        } else if (event instanceof FailLoadingEvent) {
            FailLoadingEvent e = (FailLoadingEvent) event;
            String frameInfo =
                    String.format(
                            "FailLoadingEvent 窗口[ %s | Main is %s ] [ %s ][ %s %s ]",
                            e.getFrameId(),
                            e.isMainFrame(), e.getValidatedURL(), e.getErrorCode(), e.getErrorDescription());

            System.out.println(frameInfo);
        } else if (event instanceof FrameLoadEvent) {
            FrameLoadEvent e = (FrameLoadEvent) event;
            String frameInfo =
                    String.format(
                            "FrameLoadEvent 窗口[ %s | Main is %s ]",
                            e.getFrameId(),
                            e.isMainFrame());
            System.out.println(frameInfo);
        } else if (event instanceof LoadEvent) {
            System.out.println(" LoadEvent ...");
        }

        printFramesHierarchy(event.getBrowser(), Browser.MAIN_FRAME_ID);
    }

    /**
     * 查询所有窗口
     *
     * @param browser
     * @param parentFrameId
     */
    private void printFramesHierarchy(Browser browser, long parentFrameId) {
        List<Long> childFrameIds = browser.getFramesIds(parentFrameId);
        for (Long childFrameId : childFrameIds) {
            System.out.println("Child Frame: " + childFrameId + " -> " + browser.getTitle());
//                    System.out.println("Child Frame: " + childFrameId +
//                            ", HTML: " + browser.getHTML(childFrameId));
            printFramesHierarchy(browser, childFrameId);
        }
    }

}
