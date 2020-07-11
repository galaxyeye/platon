package ai.platon.demo;

import ai.platon.pulsar.PulsarContext;
import ai.platon.pulsar.PulsarContextKt;
import ai.platon.pulsar.PulsarSession;
import ai.platon.pulsar.dom.FeaturedDocument;
import ai.platon.pulsar.persist.WebPage;
import com.google.common.collect.Lists;
import kotlin.Unit;
import org.jsoup.nodes.Element;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.IntStream;

public class DemoApplication {
    private String url = "https://list.jd.com/list.html?cat=652,12345,12349";

    public void run() throws Exception {
        var session = PulsarContext.createJvmSession();
        var page = session.load(url, "-expires 1d");
        var document = session.parse(page, false);
        var element = document.selectFirstOrNull(".sku-name");
        if (element != null) {
            var text = element.text();
            System.out.println(text);
        } else {
            System.out.println(document.getTitle());
        }

        var r = IntStream.of(1, 2, 3, 4, 5).filter(i -> i > 1).mapToObj(i -> List.of(i, i * 2));
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
