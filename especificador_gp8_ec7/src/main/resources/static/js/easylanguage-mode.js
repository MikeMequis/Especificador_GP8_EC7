// CodeMirror mode for EasyLanguage
CodeMirror.defineMode("easylanguage", function() {
  return {
    startState: function() {
      return {
        inString: false,
        inComment: false
      };
    },
    
    token: function(stream, state) {
      // Handle comments
      if (stream.match("//")) {
        stream.skipToEnd();
        return "comment";
      }
      
      // Handle strings
      if (stream.match('"')) {
        state.inString = !state.inString;
        return "string";
      }
      
      if (state.inString) {
        stream.next();
        return "string";
      }
      
      // Handle keywords
      if (stream.match(/^(algoritmo|var|inicio|fimalgoritmo|se|entao|senao|fimse|enquanto|faca|fimenquanto|inteiro|real|literal|logico)\b/, true)) {
        return "keyword";
      }
      
      // Handle built-in functions
      if (stream.match(/^(leia|escreva|escreval)\b/, true)) {
        return "builtin";
      }
      
      // Handle operators
      if (stream.match(/^(e|ou|nao)\b/, true)) {
        return "operator logic";
      }
      
      if (stream.match(/[+\-*\/=<>]=?|<>/, true)) {
        return "operator";
      }
      
      // Handle numbers
      if (stream.match(/^-?\d*\.?\d+/, true)) {
        return "number";
      }
      
      // Handle identifiers
      if (stream.match(/^[a-zA-Z_][a-zA-Z0-9_]*/, true)) {
        return "variable";
      }
      
      // Skip spaces
      if (stream.match(/^\s+/, true)) {
        return null;
      }
      
      // Handle any other character
      stream.next();
      return null;
    }
  };
});

// Register the MIME type
CodeMirror.defineMIME("text/x-easylanguage", "easylanguage"); 