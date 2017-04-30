(ns tasks.utils-test)

(def check-opts {:clojure.test.check/opts {:num-tests 10}})
(defn check-result [checks]
  (-> checks (first) (:clojure.test.check/ret) (:result)))
