(ns compojure-test.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.page :as hic-p]))

(load "generator")
(use 'markov-twister.generator)
 
(defroutes app-routes
  (GET "/" [] (hic-p/html5
               [:head  (hic-p/include-css "/css/story.css")]
               [:h3 "Welcome!"]
               [:pre [:p (files->story 500
                                       "sample-text/grimm.txt"
                                       "sample-text/alice.txt")]]))
  
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
