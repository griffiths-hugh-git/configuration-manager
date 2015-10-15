Configuration Manager
---------------------

This project aims to provide a systematic, repeatable, documented method to apply configuration changes to Java deployment artifacts.

Use cases:
-Take a Java archive (JAR, WAR, etc.), apply environment-specific values to configuration properties and produce a copy of the archive ready for deployment in each environment.
-Configure values in files of any configuration file format (i.e. properties files, XML, etc.) - any format stored in plain text inside the archive.
-Provides traceability back to the original archive by including its hash in all filenames.
-Produce a standard format for documenting parameters which must be configured for each environment.  This information can then be included in system documentation.
-Runs standalone, with minimal dependencies, to allow it to be run on controlled environments, to allow configuration properties (e.g. database passwords) to be applied only on locked-down systems.  

Usage:
-Requires: 
	An archive (and zipped format, including JAR, WAR, EAR, etc.)
	A metadata file, which describes the properties to be configured and their locations.
	A file for each environment, containing the values for each property.
-For all values to be configured, use a placeholder value.  This placeholder ("key") will be substituted with the environment values.  The value can be anything, but it should be easily identifiable.
-Write a metadata file (described below), describing the properties to be configured.
-Write an environment file (described below) for each location to which the archive will be deployed.  This file contains the values which will be configured in this environment. 
-Run the JAR with dependencies:
	java -jar configuration-manager-jar-with-dependencies.jar <archive file> <metadata file> <environment1> <environment2> ...
 A new archive is generated for each environment, in the same location as the original archive, with name prefixed with the environment ID:
 	<archive path>/<environment ID>_<original_archive_hash>_<archive name>
 	
Metadata file:
-The metadata file is a CSV.  Each row must consist of exactly three entries:
	key,description,path
-The key is the value which will be substituted for.  
-The description is for documentation purposes - it describes the purpose of this property.  It can be surrounded by quotes if required - for full escaping rules, see the documentation for OpenCSV.
-The path is the location of the file in which the key appears within the archive.
-If a key appears in more than one file within the archive, each file must be entered on a separate row.
-An example template is provided in src/main/resources, and further examples are in src/test/resources.

Environment files:
-The environment is a Java properties file.  
-It must contain a key "environment_id".  This is used to identify the environment, and is used to prefix the modified archive name.
-Each environment file must contain values for all properties in the corresponding metadata file, and only these.  Any missing properties, or any extra properties, will cause an error.
-A template environment file is provided inn src/main/resources, and further examples are in src/test/resources.
