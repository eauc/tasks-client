(ns tasks.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.util.response :as response]))

(defroutes app-routes
  (GET "/" []
       (response/content-type
        (response/resource-response "index.html" {:root "public"})
        "text/html")))

(def app
  (wrap-defaults app-routes site-defaults))
