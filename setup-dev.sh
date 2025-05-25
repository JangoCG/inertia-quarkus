#!/bin/bash

# Development Setup Script for Quarkus Inertia.js Extension
# This script helps set up the development environment

set -e

echo "🚀 Setting up Quarkus Inertia.js Extension development environment..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or later."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 or later is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version check passed"

# Check if Maven is installed or use wrapper
if command -v mvn &> /dev/null; then
    MVN_CMD="mvn"
    echo "✅ Maven found in system PATH"
else
    MVN_CMD="./mvnw"
    echo "✅ Using Maven wrapper"
fi

# Make Maven wrapper executable if it exists
if [ -f "./mvnw" ]; then
    chmod +x ./mvnw
fi

# Clean and compile the project
echo "🔧 Building the project..."
$MVN_CMD clean compile

# Run tests
echo "🧪 Running tests..."
$MVN_CMD test

# Install to local repository
echo "📦 Installing to local Maven repository..."
$MVN_CMD install -DskipTests

# Check if Quarkus CLI is available
if command -v quarkus &> /dev/null; then
    echo "✅ Quarkus CLI is available"
    echo "💡 You can test the extension with: quarkus create app test-app && cd test-app && quarkus ext add com.gurtus:quarkus-inertia"
else
    echo "⚠️  Quarkus CLI not found. Install it from: https://quarkus.io/guides/cli-tooling"
    echo "💡 You can still test by adding the extension to an existing Quarkus project"
fi

# Create a simple test project structure for development
echo "📁 Creating development test project..."
mkdir -p dev-test
cat > dev-test/pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>inertia-test</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <quarkus.platform.version>3.15.0</quarkus.platform.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.quarkus.platform</groupId>
                <artifactId>quarkus-bom</artifactId>
                <version>${quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <dependencies>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-arc</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-resteasy-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>com.gurtus</groupId>
            <artifactId>quarkus-inertia</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>io.quarkus.platform</groupId>
                <artifactId>quarkus-maven-plugin</artifactId>
                <version>${quarkus.platform.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
EOF

echo "✨ Development environment setup complete!"
echo ""
echo "Next steps:"
echo "1. 📖 Read CONTRIBUTING.md for development guidelines"
echo "2. 🧪 Test your changes with: cd dev-test && mvn quarkus:dev"
echo "3. 🔄 Rebuild extension with: $MVN_CMD clean install"
echo "4. 📚 Check the documentation in README.md"
echo "5. 🚀 Ready to contribute? See PUBLISHING.md for release process"
echo ""
echo "Happy coding! 🎉"
