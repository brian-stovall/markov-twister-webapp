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
                          "sample-text/alice.txt")]]))

    (GET "/mix-story" []
       (hic-p/html5
        [:head  (hic-p/include-css "/css/story.css")]
        [:h3 "Choose stories from the library:"]
        (hic-f/form-to [:post "/my-story"]
                       (hic-f/label :alice "Alice in Wonderland")
                       (hic-f/check-box :alice false "sample-text/alice.txt")
                       [:br]
                       (hic-f/label :sherlock "The Adventures of Sherlock Holmes")
                       (hic-f/check-box :sherlock false "sample-text/sherlock.txt")
                       [:br]
                       (hic-f/label :conneticut "A Conneticut Yankee in King Arthur's Court")
                       (hic-f/check-box :conneticut false "sample-text/conneticut.txt")
                       [:br]
                       (hic-f/label :critique "A Critique of Pure Reason")
                       (hic-f/check-box :critique false "sample-text/critique.txt")
                       [:br]
                       (hic-f/label :divine "The Divine Comedy")
                       (hic-f/check-box :divine false "sample-text/divine.txt")
                       [:br]
                       (hic-f/label :dunwich "The Dunwich Horror")
                       (hic-f/check-box :dunwich false "sample-text/dunwich.txt")
                       [:br]
                       (hic-f/label :ethics "Ethics")
                       (hic-f/check-box :ethics false "sample-text/ethics.txt")
                       [:br]
                       (hic-f/label :grimm "Grimm Fairytales")
                       (hic-f/check-box :grimm false "sample-text/grimm.txt")
                       [:br]
                       (hic-f/label :macbeth "Macbeth")
                       (hic-f/check-box :macbeth false "sample-text/macbeth.txt")
                       [:br]
                       (hic-f/label :man "The Man With Two Left Feet")
                       (hic-f/check-box :man false "sample-text/man.txt")
                       [:br]
                       (hic-f/label :munch "Mr. Munchausen")
                       (hic-f/check-box :munch false "sample-text/munchausen.txt")
                       [:br]
                       (hic-f/label :mysticism "Mysticism and Logic")
                       (hic-f/check-box :mysticism false "sample-text/mysticism.txt")
                       [:br]
                       (hic-f/label :nonsense "Nonsense Books")
                       (hic-f/check-box :nonsense false "sample-text/nonsense.txt")
                       [:br]
                       (hic-f/label :paradise "Paradise Lost")
                       (hic-f/check-box :paradise false "sample-text/paradise.txt")
                       [:br]
                       (hic-f/label :poe "The Works of Edgar Allen Poe: Vol. One")
                       (hic-f/check-box :poe false "sample-text/poe.txt")
                       [:br]
                       (hic-f/label :pride "Pride and Prejudice")
                       (hic-f/check-box :pride false "sample-text/pride.txt")
                       [:br]
                       (hic-f/label :qix "Don Quixote")
                       (hic-f/check-box :qix false "sample-text/qix.txt")
                       [:br]
                       (hic-f/label :tale "A Tale of Two Cities")
                       (hic-f/check-box :tale false "sample-text/tale.txt")
                       [:br]
                       (hic-f/label :trial "The Trial")
                       (hic-f/check-box :trial false "sample-text/trial.txt")
                       [:br]
                       (hic-f/label :white "White Nights")
                       (hic-f/check-box :white false "sample-text/white.txt")
                       [:br]
                       (r-a-f/anti-forgery-field)
                       (hic-f/submit-button "Write it!"))))

    (POST "/my-story"
          req
          (hic-p/html5
           [:head  (hic-p/include-css "/css/story.css")]
           [:pre [:p (apply files->story
                      500
                      (vals (select-keys (:params req)
                                           [:alice :sherlock :conneticut :critique
                                            :divine :qix :dunwich :ethics :grimm
                                            :macbeth :man :munch :mysticism :nonsense
                                            :paradise :poe :pride :qix :tale
                                            :trial :white])))]]))
  
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
