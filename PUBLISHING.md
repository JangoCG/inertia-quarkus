# Publishing


```sh
# bump all versions
mvn versions:set -DnewVersion=0.2.0 -DprocessAllModules=true -DgenerateBackupPoms=false

# Deploy
mvn clean deploy

# see
https://github.com/JangoCG/inertia-quarkus/packages
```