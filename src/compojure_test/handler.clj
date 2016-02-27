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
               (hic-e/link-to "/upload-files" [:p "Upload your own files to make a story."])))

  (GET "/upload-files" [] (hic-p/html5
                           (hic-f/form-to [:post "/make-story"]
                                          (hic-f/label :file "Choose a file:")
                                          (hic-f/file-upload :file)
                                          (r-a-f/anti-forgery-field)
                                          (hic-f/submit-button "Write it!"))))

  (POST "/make-story"
        [req]
        ;(io/copy (:body req) (io/output-stream (io/file "body.txt")))
        (hic-p/html5
         [:p (str (:body req))]))
  
  (GET "/demo" [] (hic-p/html5
               [:head  (hic-p/include-css "/css/story.css")]
               [:pre [:p (files->story
                          500
                          "sample-text/grimm.txt"
                          "sample-text/alice.txt"
                          "sample-text/marx.txt")]]))
  
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
