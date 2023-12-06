package fallout.proyecto_poo.data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public record LevelData
(
        String name,
        String map,
        List<WaveData> waves

)
{
    public int maxWaveIndex() {
        return waves()
                .stream()
                .max(Comparator.comparingInt(WaveData::index))
                .get()
                .index();
    }
    public Stream<WaveData> waves(int index) {
        return waves()
                .stream()
                .filter(w -> w.index() == index);
    }
}
