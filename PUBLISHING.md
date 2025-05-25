# Publishing Guide

This guide walks you through the steps needed to publish the Quarkus Inertia.js extension to Maven Central and the Quarkus Extension Registry.

## Prerequisites

### 1. Sonatype OSSRH Account

Create an account at [Sonatype OSSRH](https://central.sonatype.org/register/central-portal/) to publish to Maven Central.

1. Register at https://central.sonatype.org/register/central-portal/
2. Verify your namespace (com.gurtus) by following their verification process
3. Note your username and password for later use

### 2. GPG Key Setup

You'll need a GPG key to sign your artifacts:

```bash
# Generate a new GPG key
gpg --gen-key

# List your keys to get the key ID
gpg --list-secret-keys --keyid-format LONG

# Export your public key to upload to key servers
gpg --armor --export YOUR_KEY_ID

# Export your private key for GitHub secrets
gpg --armor --export-secret-keys YOUR_KEY_ID
```

Upload your public key to key servers:

```bash
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
gpg --keyserver keys.openpgp.org --send-keys YOUR_KEY_ID
```

### 3. GitHub Secrets Configuration

Add the following secrets to your GitHub repository (Settings → Secrets and variables → Actions):

- `OSSRH_USERNAME`: Your Sonatype username
- `OSSRH_TOKEN`: Your Sonatype password
- `GPG_SECRET_KEY`: Your private GPG key (output from gpg --armor --export-secret-keys)
- `GPG_PASSPHRASE`: Your GPG key passphrase

### 4. Local Maven Settings

Create or update `~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${env.OSSRH_USERNAME}</username>
      <password>${env.OSSRH_TOKEN}</password>
    </server>
  </servers>

  <profiles>
    <profile>
      <id>ossrh</id>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <gpg.keyname>YOUR_KEY_ID</gpg.keyname>
        <gpg.passphrase>${env.GPG_PASSPHRASE}</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

## Publishing Process

### Option 1: Automated Release via GitHub Actions

1. Create a release tag:

   ```bash
   git tag -a v1.0.0 -m "Release version 1.0.0"
   git push origin v1.0.0
   ```

2. The release workflow will automatically:
   - Build and test the project
   - Deploy to Maven Central staging
   - Release to Maven Central (if successful)

### Option 2: Manual Release

1. Prepare the release:

   ```bash
   ./mvnw clean install
   ./mvnw versions:set -DnewVersion=1.0.0
   ```

2. Deploy to staging:

   ```bash
   ./mvnw clean deploy -Prelease
   ```

3. Release from staging (if tests pass):
   ```bash
   ./mvnw nexus-staging:release -Prelease
   ```

## Quarkus Extension Registry Submission

After publishing to Maven Central:

1. Fork the [Quarkus Extension Registry](https://github.com/quarkusio/quarkus-extension-registry)
2. Add your `quarkus-inertia.yaml` file to the appropriate directory
3. Create a pull request

The `quarkus-inertia.yaml` file is already prepared in the project root.

## Alternative: Quarkiverse

Consider joining [Quarkiverse](https://github.com/quarkiverse) for easier publishing:

1. Create an issue at https://github.com/quarkiverse/quarkiverse/issues
2. Request to move your extension to Quarkiverse
3. Benefits: Automated publishing, community support, easier maintenance

## Verification

After publishing, verify your extension:

1. Check it appears on [Maven Central](https://search.maven.org/)
2. Test installation: `quarkus ext add com.gurtus:quarkus-inertia`
3. Verify it appears in the [Quarkus Extension Catalog](https://quarkus.io/extensions/)

## Troubleshooting

### Common Issues

1. **GPG signing fails**: Ensure your GPG key is properly configured and the passphrase is correct
2. **Sonatype authentication fails**: Verify your OSSRH credentials
3. **Staging repository not found**: Check if the deployment succeeded before trying to release
4. **Extension not appearing in catalog**: Ensure the quarkus-extension.yaml metadata is correct

### Getting Help

- [Sonatype OSSRH Guide](https://central.sonatype.org/publish/publish-guide/)
- [Quarkus Extension Authors Guide](https://quarkus.io/guides/extension-authors-guide)
- [Quarkiverse Documentation](https://github.com/quarkiverse/quarkiverse/wiki)

## Next Steps After First Release

1. Set up automated dependency updates (Dependabot/Renovate)
2. Add integration tests for different Quarkus versions
3. Consider adding example projects
4. Set up documentation website (GitHub Pages)
5. Engage with the Quarkus community
