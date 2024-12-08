package space.nerfthis.data;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class JSONBuilder {


    public static String buildJson(List<Point> dataList) {

        return dataList.stream()
                .map(data -> String.format(Locale.US, "{\"x\": %.10f, \"y\": %.10f, \"r\": %f, \"flag\": %s}",
                        data.getX(), data.getY(), data.getR(), data.isFlag()))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}