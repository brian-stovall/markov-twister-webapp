(ns compojure-test.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.anti-forgery :as r-a-f]
            [hiccup.page :as hic-p]
            [hiccup.element :as hic-e]
            [hiccup.form :as hic-f]
            [clojure.java.io :as io]))

(load "generator")
(use 'markov-twister.generator)
 
(defroutes app-routes
  (GET "/" [] (hic-p/html5
               [:h2 "Places you can go:"]
               (hic-e/link-to "/demo" [:p "Demo story from Grimm, Alice, and Marx"])
               (hic-e/link-to "/mix-story" [:p "Choose files from our library to make a story."])))

  (GET "/demo" [] (hic-p/html5
               [:head  (hic-p/include-css "/css/story.css")]
               [:pre [:p (files->story
                          500
                          "sample-text/grimm.txt"
                          "sample-text/alice.txt"
                          "sample-text/marx.txt")]]))

    (GET "/mix-story" []
       (hic-p/html5
        [:h3 "Choose stories from the library"]
        (hic-f/form-to [:post "/my-story"]
                       (hic-f/label :alice "Alice in Wonderland")
                       (hic-f/check-box :alice false "sample-text/alice.txt")
                       (hic-f/label :grimm "Grimm Fairytales")
                       (hic-f/check-box :grimm false "sample-text/grimm.txt")
                       (hic-f/label :marx "Karl Marx")
                       (hic-f/check-box :marx false "sample-text/marx.txt")
                       (r-a-f/anti-forgery-field)
                       (hic-f/submit-button "Write it!"))))

    (POST "/my-story"
          req
          (hic-p/html5
           [:pre [:p (apply files->story
                      500
                      (vals (select-keys (:params req) [:alice :marx :grimm])))]]))
  
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
