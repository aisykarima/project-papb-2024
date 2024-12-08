package ak.mobile.aduinajafinal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Laporan>> laporanListLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Laporan>> getLaporanList() {
        return laporanListLiveData;
    }

    public void addLaporan(Laporan laporan) {
        List<Laporan> currentList = laporanListLiveData.getValue();
        if (currentList != null) {
            currentList.add(laporan);
            laporanListLiveData.setValue(currentList);
        }
    }
}
