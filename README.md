# majumble

A quick and dirty matrix manipulation postfix language. Uses [instaparse][] for
parsing and [vectorz-clj][] for the actual work.

There is no real point to this language and project I just wanted to play with
[instaparse][]. If you really want there to be meaning though you could tell
yourself that the postfix notation allows you to do matrix manipulations with
less typing than if you used [clojure.core.matrix][] functions directly.

##### Clojure
```clojure
(require '[clojure.core.matrix :as ma])

(ma/mul (ma/transpose [[1 2] [3 4]]) [[1 2] [2 1]])
```

##### majumble
```clojure
(require '[majumble.core :refer [parse])

(parse "[[1 2] [3 4]]' [[1 2] [2 1]] *")
```


#### Inspiration

I suppose this was sorta kinda loosely inspired by [J][].

[clojure.core.matrix]: https://github.com/mikera/matrix-api
[instaparse]: https://github.com/Engelberg/instaparse
[J]: http://www.jsoftware.com/
[vectorz-clj]: https://github.com/mikera/vectorz-clj
