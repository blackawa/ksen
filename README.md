```
┌─┐
│ │┌─┐
│ ││ │ ┌─────┐┌─────┐┌─────┐
│ └┘ └┐│ ┌───┘│ ┌─┐ ││ ┌─┐ │
│ ┌─┐ ││ └───┐│ └─┘ ││ │ │ │
│ │ │ │└───┐ ││ ┌───┘│ │ │ │
│ │ │ │┌───┘ ││ └───┐│ │ │ │
└─┘ └─┘└─────┘└─────┘└─┘ └─┘
```

# ksen

Parse tables drawn with box-drawing characters.
I dedicate this library to unfortunate friend who have to parse crazy text data like me.

## Usage
```
user> (require '[ksen.core :as ksen])
user> (ksen/read-str some-table)
```

Look tests for input and output.

## License

Copyright © 2019 blackawa

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

## Development

```
;; to evaluate source code
user> (refresh)
;; to run all tests
user> (test)
;; to run specific tests
user> (test #'ksen.core-test/xxx)
```
