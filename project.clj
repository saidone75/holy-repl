(defproject org.saidone/holy-repl "1.0.1"
  :description "The Holy BIBL in your REPL"
  :url "https://saidone.org"
  :license {:name "MIT License"
            :url  "https://github.com/saidone75/holy-repl/blob/main/LICENSE"}
  :dependencies [[org.clojure/clojure "1.11.3"]
                 [org.clojure/data.xml "0.0.8"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
