package ai.platon.demo;

import ai.platon.pulsar.PulsarContext;
import ai.platon.pulsar.PulsarContextKt;
import ai.platon.pulsar.PulsarSession;
import ai.platon.pulsar.dom.FeaturedDocument;
import ai.platon.pulsar.persist.WebPage;
import kotlin.Unit;
import org.jsoup.nodes.Element;

public class DemoApplication {
    private String url = "https://list.jd.com/list.html?cat=652,12345,12349";

    public void run() throws Exception {
        PulsarSession session = PulsarContext.createJvmSession();
        WebPage page = session.load(url, "-expires 1d");
        FeaturedDocument document = session.parse(page, false);
        Element element = document.selectFirstOrNull(".sku-name");
        if (element != null) {
            String text = element.text();
            System.out.println(text);
        } else {
            System.out.println(document.getTitle());
        }
    }

    public static void main(String[] args) {
        PulsarContextKt.withPulsarContext(() -> {
            try {
                new DemoApplication().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Unit.INSTANCE;
        });
    }
}
