(ns holy-repl.core-test
  (:require [clojure.test :refer :all]
            [holy-repl.core :refer :all]))

(deftest Luke-6-21
  (is (= (first (Luke "6:21")) "Blessed are ye that hunger now: for ye shall be filled. Blessed are ye that weep now: for ye shall laugh.")))

(deftest Luke-12-28
  (is (= (first (Luke "12:38")) "And if he shall come in the second watch, and if in the third, and find them so, blessed are those servants.")))

(deftest print-Luke-6-21
  (print (Luke "6:21")))
