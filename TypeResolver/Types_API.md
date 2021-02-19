# Type API

methods
- add method
- add attribute
- get method
- get attribute
- is already covariant to
- must be subtype of
- is already subtype of
- become
- can become

# Defined Types
Attributes:
- name
- package name
- attributes
- methods
- extends
- implements

methods:
- init
- add method
- add attribute
- get method
- get attribute
- is covariant to
- extends
- implements

# Referenced Types
Attributes:
- name
- package name
- attribute requirements (must-haves)
- method requirements (must-haves)
- confirmed super types

Methods:
- init
- add method (adds a new requirement-return is unknown)
- add attribute (adds a new attribute requirement - type is unknown)
- get method
- get attribute
- must be subtype of
- is already subtype of

# Unknown Types
Attributes:
- name
- attributes requirements (must-haves)
- method requirements (must-haves)
- confirmed super types
- real type

Methods:
- init
- add method (adds a new requirement - return is unknown)
- add attribute (adds a new attribute requirement - type is unknown)
- get method
- get attribute
- must be subtype of
- become
- can become
- is already subtype of