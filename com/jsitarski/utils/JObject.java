package com.jsitarski.utils;
 
public class JObject<K, V> {
        protected K key;
        protected V value;
 
        public JObject(K key, V value) {
                this.key = key;
                this.value = value;
        }
 
        public K getKey() {
                return key;
        }
 
        public void setKey(K key) {
                this.key = key;
        }
 
        public V getValue() {
                return value;
        }
 
        public void setValue(V value) {
                this.value = value;
        }
 
        @Override
        public String toString() {
                return "JObject[key(" + key.getClass().getName() + ")= " + key
                                + ", value(" + value.getClass().getName() + ")= " + value + "]";
        }
 
        public static void main(String[] args) {
                JObject<String, Integer> object = new JObject<String, Integer>("Key",
                                15);
                System.out.println(object);
        }
}