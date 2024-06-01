(ns holy-repl.core
  (:gen-class)
  (:import (java.io FileReader)))

(require '[clojure.data.xml :as xml]
         '[clojure.string :as s]
         '[clojure.walk :as w])

(defn- testaments []
  (filter
   #(= "testament" (:type (:attrs %)))
   (-> (xml/parse (FileReader. "resources/American Standard Version.xml"))
       :content
       (nth 0)
       :content)))

(defn books []
  (map
   #(if (re-matches #"^\d.*" %)
      (apply str (conj (vec (rest %)) (first %)))
      %)
   (reduce
    #(when (= "book" (:type (:attrs %2)))
       (conj %1 (:osisID (:attrs %2))))
    []
    (flatten (map :content (testaments))))))

(defn- parse-args [args]
  (when-not (s/blank? args)
    (let [[chapter verses]
          (s/split args #"[\.\:]")]
      {:chapter chapter
       :verses verses })))

(defn- get-book [book-name]
  (->>
   (filter
    #(= book-name (:osisID (:attrs %)))
    (flatten (map :content (testaments))))
   first
   :content))

(defn- get-chapter [book-name chapter]
  (->>
   (filter
    #(= (str book-name "." chapter) (:osisID (:attrs %)))
    (get-book book-name))
   first
   :content))

(defn- get-verse [book-name chapter verse]
  (apply
   str
   (flatten
    (w/postwalk
     #(if (instance? clojure.data.xml.Element %)
        (:content %)
        %)
     (filter
      #(= (str book-name "." chapter "." verse) (:osisID (:attrs %)))
      (get-chapter book-name chapter))))))

(defn- get-verses [book-name chapter verses]
  (let [[f l] (s/split verses #"-")
        l (if (nil? l) f l)]
    (map
     (partial get-verse book-name chapter)
     (range (Integer/parseInt f) (inc (Integer/parseInt l))))))

(defn- read-book [book-name args]
  (let [book-name (if (re-matches #"^.*\d$" book-name)
                    (apply str (conj (butlast book-name) (last book-name)))
                    book-name)
        {chapter :chapter verses :verses} (parse-args (first args))]
    (cond
      (nil? chapter) (get-book book-name)
      (nil? verses) (get-chapter book-name chapter)
      :else (get-verses book-name chapter verses))))

(run!
 #(intern *ns* (symbol %)
          (fn [& args] (read-book % args)))
 (books))

(defn random-verse []
  (let [book-name (first (shuffle (books)))
        book (ns-resolve *ns* (symbol book-name))
        book-map (zipmap (range 1 (inc (count (book)))) (map #(inc (rand-int (count (:content %)))) (book)))
        chapter (rand-nth (keys book-map))]
    (first {(str book-name " " chapter ":" (get book-map chapter)) (book (str chapter ":" (get book-map chapter)))})))

(defn random-verse-str []
  ((comp first val) (random-verse)))