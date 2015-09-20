package org.danielli.xultimate.context.kvStore.db.support;

import org.danielli.xultimate.context.kvStore.db.KeyValueStoreException;
import org.danielli.xultimate.context.kvStore.db.KeyValueIterator;
import org.danielli.xultimate.context.kvStore.db.KeyValueStore;
import org.rocksdb.*;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * RocksDB K/V存储。
 *
 * @author Daniel Li
 * @since 13 September 2015
 */
public class RocksDbKeyValueStore implements KeyValueStore<byte[], byte[]> {

    private final File dir;
    private final Options options;
    private final WriteOptions writeOptions;
    private RocksDB db;

    public RocksDbKeyValueStore(File dir, Options options, WriteOptions writeOptions) {
        this.dir = dir;
        this.options = options;
        this.writeOptions = writeOptions;
        try {
            this.db = RocksDB.open(this.options, this.dir.toString());
        } catch (RocksDBException e) {
            throw new KeyValueStoreException(e);
        }
    }

    @Override
    public byte[] get(byte[] key) throws KeyValueStoreException {
        Assert.notNull(key);
        try {
            return this.db.get(key);
        } catch (RocksDBException e) {
            throw new KeyValueStoreException(e);
        }
    }

    @Override
    public void put(byte[] key, byte[] value) throws KeyValueStoreException {
        Assert.notNull(key);
        try {
            if (value == null) {
                this.db.remove(this.writeOptions, key);
            } else {
                this.db.put(this.writeOptions, key, value);
            }
        } catch (RocksDBException e) {
            throw new KeyValueStoreException(e);
        }
    }

    @Override
    public void putAll(List<KeyValueIterator.Entry<byte[], byte[]>> entries) throws KeyValueStoreException {
        Iterator<KeyValueIterator.Entry<byte[], byte[]>> iter = entries.iterator();
        while (iter.hasNext()) {
            KeyValueIterator.Entry<byte[], byte[]> curr = iter.next();
            try {
                if (curr.getValue() == null) {
                    this.db.remove(this.writeOptions, curr.getKey());
                } else {
                    byte[] key = curr.getKey();
                    byte[] value = curr.getValue();
                    this.db.put(this.writeOptions, key, value);
                }
            } catch (RocksDBException e) {
                throw new KeyValueStoreException(e);
            }
        }
    }

    @Override
    public void remove(byte[] key) throws KeyValueStoreException {
        this.put(key, null);
    }

    @Override
    public KeyValueIterator<byte[], byte[]> range(byte[] from, byte[] to, Comparator<byte[]> comparator) {
        Assert.notNull(from);
        Assert.notNull(to);
        Assert.notNull(comparator);
        RocksIterator iter = this.db.newIterator();
        return new RocksDbKeyValueStore.RocksDbRangeIterator(iter, from, to, comparator);
    }

    @Override
    public KeyValueIterator<byte[], byte[]> all() {
        RocksIterator iter = this.db.newIterator();
        iter.seekToFirst();
        return new RocksDbKeyValueStore.RocksDbIterator(iter);
    }

    public void flush(boolean waitForFlush) {
        try {
            this.db.flush(new FlushOptions().setWaitForFlush(waitForFlush));
        } catch (RocksDBException e) {
            throw new KeyValueStoreException(e);
        }
    }

    @Override
    public void flush() {
        flush(true);
    }

    @Override
    public void close() {
        this.db.close();
    }

    /**
     * K/V迭代器。
     *
     * @author Daniel Li
     * @since 13 September 2015
     */
    public class RocksDbIterator implements KeyValueIterator<byte[], byte[]> {
        private final RocksIterator iter;
        private boolean open;

        @Override
        public void close() {
            this.open = false;
            this.iter.dispose();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("RocksDB iterator doesn\'t support remove");
        }

        @Override
        public boolean hasNext() {
            return this.iter.isValid();
        }

        public Entry<byte[], byte[]> getEntry() {
            byte[] key = this.iter.key();
            byte[] value = this.iter.value();
            return new Entry(key, value);
        }

        @Override
        public Entry<byte[], byte[]> next() {
            if (this.hasNext()) {
                Entry entry = this.getEntry();
                this.iter.next();
                return entry;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void finalize() {
            if (this.open) {
                this.close();
            }
        }

        public RocksDbIterator(RocksIterator iter) {
            this.iter = iter;
            this.open = true;
        }
    }

    /**
     * 范围迭代器。
     *
     * @author Daniel Li
     * @since 13 September 2015
     */
    public class RocksDbRangeIterator extends RocksDbKeyValueStore.RocksDbIterator {
        private final byte[] to;
        private final Comparator<byte[]> comparator;

        public Comparator<byte[]> comparator() {
            return this.comparator;
        }

        @Override
        public boolean hasNext() {
            return super.hasNext() && this.comparator().compare(this.getEntry().getKey(), this.to) < 0;
        }

        public RocksDbRangeIterator(RocksIterator iter, byte[] from, byte[] to, Comparator<byte[]> comparator) {
            super(iter);
            this.to = to;
            this.comparator = comparator;
            iter.seek(from);
        }
    }
}
