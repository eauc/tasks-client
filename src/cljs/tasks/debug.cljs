(ns tasks.debug)

(defonce debug?
  ^boolean js/goog.DEBUG)

(defn setup []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))

(defn spy
  ([label]
   (when debug?
     (.log js/console label)))
  ([label data]
   (when debug?
     (.log js/console label data))
   data))
