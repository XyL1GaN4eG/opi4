package space.nerfthis.data;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class PointStats extends NotificationBroadcasterSupport implements PointStatsMBean {
    private int totalPoints = 0;
    private int missCount = 0;
    private int consecutiveMisses = 0;
    private long sequenceNumber = 1L;

    @Override
    public synchronized int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public synchronized int getMissCount() {
        return missCount;
    }

    @Override
    public synchronized void reset() {
        totalPoints = 0;
        missCount = 0;
        consecutiveMisses = 0;
    }

    /**
     * Вызывать при добавлении новой точки.
     */
    public synchronized void notifyNewPoint(Point p) {
        totalPoints++;
        boolean inside = GeometryValidator.isInsideArea(p.getX(), p.getY(), p.getR());
        if (!inside) {
            missCount++;
            consecutiveMisses++;
            if (consecutiveMisses >= 4) {
                Notification notif = new Notification(
                        "space.nerfthis.jmx.pointstats.misses",
                        this,
                        sequenceNumber++,
                        System.currentTimeMillis(),
                        "Пользователь совершил 4 промаха подряд"
                );
                sendNotification(notif);
                // Сбросим подряд промахи, если хотим отлавливать следующие 4
                consecutiveMisses = 0;
            }
        } else {
            // промахи подряд обрываются
            consecutiveMisses = 0;
        }
    }
}
