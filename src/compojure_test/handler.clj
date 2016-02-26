(ns compojure-test.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.page :as hic-p]))

(load "generator")
(use 'markov-twister.generator)
 
(defroutes app-routes
  (GET "/" [] (hic-p/html5 [:h3 "Welcome!"]
                           [:p (file->story "sample-text/grimm.txt" 50)]))
  
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
