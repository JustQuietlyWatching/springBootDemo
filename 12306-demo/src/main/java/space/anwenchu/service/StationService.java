package space.anwenchu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.anwenchu.utils.StationCodeUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by an_wch on 2018/2/8.
 */
@Service
@Slf4j
public class StationService {
    private final static Map<String,StationCodeUtil.StationCode> stationCodes = StationCodeUtil.getData();

    public List<StationCodeUtil.StationCode> getStationList() {
        List<StationCodeUtil.StationCode> stationCodeList = new List<StationCodeUtil.StationCode>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<StationCodeUtil.StationCode> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(StationCodeUtil.StationCode stationCode) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends StationCodeUtil.StationCode> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends StationCodeUtil.StationCode> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public StationCodeUtil.StationCode get(int index) {
                return null;
            }

            @Override
            public StationCodeUtil.StationCode set(int index, StationCodeUtil.StationCode element) {
                return null;
            }

            @Override
            public void add(int index, StationCodeUtil.StationCode element) {

            }

            @Override
            public StationCodeUtil.StationCode remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<StationCodeUtil.StationCode> listIterator() {
                return null;
            }

            @Override
            public ListIterator<StationCodeUtil.StationCode> listIterator(int index) {
                return null;
            }

            @Override
            public List<StationCodeUtil.StationCode> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        stationCodes.forEach((k,v) -> {
            stationCodeList.add(stationCodes.get(k));
        });
        return StationCodeUtil.getDataList();
    }
}
